type ConfigurationParameterIntersection = ConfigurationParameter & {
  key: string;
  category: string;
  value: number | string;
  unit: string;
  description: string;
  intersectionID: number;
  roadRegulatorID: string;
  rsuID: string;
};
