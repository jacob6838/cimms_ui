import { useState, useCallback, useEffect } from 'react';
import Head from 'next/head';
import { useRouter } from 'next/router'
import { Box, Container, Typography } from '@mui/material';
import { configParamApi } from '../../../../apis/configuration-param-api';
import { AuthGuard } from '../../../../components/authentication/auth-guard';
import { DashboardLayout } from '../../../../components/dashboard/dashboard-layout';
import { ConfigParamEditForm } from '../../../../components/dashboard/configuration/configuration-edit-form';
import { useMounted } from '../../../../hooks/use-mounted';
import { gtm } from '../../../../lib/gtm';

const ConfigParamEdit = () => {
    const isMounted = useMounted();
    const [parameter, setParameter] = useState(null);

    const router = useRouter()
    const { parameterName } = router.query

    useEffect(() => {
        gtm.push({ event: 'page_view' });
    }, []);
    
    const getParameter = useCallback(async (name) => {
        try {
            const data = await configParamApi.getParameter(name);

            if (isMounted()) {
                setParameter(data);
            }
        } catch (err) {
            console.error(err);
        }
    }, [isMounted]);

    useEffect(() => {
        getParameter(parameterName);
    },
        []);

    if (!parameter) {
        return null;
    }

    return (
        <>
            <Head>
                <title>
                    Parameter Edit
                </title>
            </Head>
            <Box
                component="main"
                sx={{
                    backgroundColor: 'background.default',
                    flexGrow: 1,
                    py: 8
                }}
            >
                <Container maxWidth="md">
                    <Box
                        sx={{
                            alignItems: 'center',
                            display: 'flex',
                            overflow: 'hidden'
                        }}
                    >
                        <div>
                            <Typography
                                noWrap
                                variant="h4"
                            >
                                {parameter.name}
                            </Typography>
                        </div>
                    </Box>
                    <Box mt={3}>
                        <ConfigParamEditForm parameter={parameter} configParamApi={configParamApi} />
                    </Box>
                </Container>
            </Box>
        </>
    );
};

ConfigParamEdit.getLayout = (page) => (
    <AuthGuard>
        <DashboardLayout>
            {page}
        </DashboardLayout>
    </AuthGuard>
);

export default ConfigParamEdit;
