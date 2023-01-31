import re
import os

matching_expr = r"    private ([a-zA-Z0-9_<>]*?) ([a-zA-Z_]*)"
matching_expr_extends = r"private class ([a-zA-Z0-9_<>]*?) extends ([a-zA-Z_]*)\{"

TYPE_MAPPINGS = {
    # Simple types
    '(^String)': 'str',

    '(^int)': 'number',
    '(^Integer)': 'number',
    '(^Double)': 'number',
    '(^double)': 'number',
    '(^long)': 'number',
    '(^Long)': 'number',

    '(^Boolean)': 'boolean',
    '(^bool)': 'boolean',

    '^ArrayList<(.*?)>': r'\1[]',
    '^List<(.*?)>': r'\1[]',

    '(^LineString)': 'number[][]',
    '(^ZonedDateTime)': 'Date',

    # '(Set<(.*?)>)': r'Set<\1>',
}

IMPORTS_PATH = "./ts/imports"
TEMPLATE = """{imports}
type {class_name} = {extension}{{
{contents}
}};"""

if not os.path.exists(IMPORTS_PATH):
    os.mkdir(IMPORTS_PATH)


files = [os.path.join(dp, f) for dp, dn, filenames in os.walk('./java/')
         for f in filenames if os.path.splitext(f)[1] == '.java']

for file_path in files:

    file_path = '.'.join(file_path.replace('\\', '/').split('.')[:-1])
    JAVA_PATH = '/'.join(file_path.split('/')[:-1])
    TS_PATH = JAVA_PATH.replace('java', 'ts')
    CLASS_NAME = file_path.split('/')[-1]

    file_contents = open(f"{JAVA_PATH}/{CLASS_NAME}.java", 'r').read()
    extension = re.findall(matching_expr_extends, file_contents)
    extension = f"{extension[0][1]} &" if extension else ""
    matches = re.findall(matching_expr, file_contents)
    print(matches)
    lines = []
    for var in matches:
        ts_type = var[0]
        ts_name = var[1]
        for k, v in TYPE_MAPPINGS.items():
            ts_type = re.sub(k, v, ts_type)
        lines.append(f"  {ts_name}?: {ts_type}")

    ts_contents = TEMPLATE.format(
        imports="", class_name=CLASS_NAME, extension=extension, contents='\n'.join(lines))

    if not os.path.exists(f"{TS_PATH}/"):
        os.makedirs(f"{TS_PATH}/")
    with open(f"{TS_PATH}/{CLASS_NAME}.d.ts", 'w') as f:
        f.write(ts_contents)
