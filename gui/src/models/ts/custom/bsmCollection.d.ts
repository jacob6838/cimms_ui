type BsmFeatureCollection = {
    type: "FeatureCollection",
    features: BsmFeature[]
}

type BsmFeature = {
    type: "Feature",
    properties: J2735BsmCoreData,
    geometry: PointGemetry,
}

type PointGemetry = {
    type: "Point",
    coordinates: number[]
}