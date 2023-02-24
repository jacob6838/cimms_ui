import { Avatar, Box, Card, CardContent, Grid, Typography, IconButton } from "@mui/material";
import MapRoundedIcon from "@mui/icons-material/MapRounded";
import React from "react";
import NextLink from "next/link";

export const SignalStateAssessmentCard = (props: { assessment: SignalStateAssessment | null }) => {
  const { assessment } = props;
  return (
    <Card sx={{ height: "100%" }}>
      <CardContent>
        <Grid container spacing={3} sx={{ justifyContent: "space-between" }}>
          <Grid item>
            <Typography color="textSecondary" gutterBottom variant="overline">
              Signal State Assessment
            </Typography>
            {assessment == null
              ? ""
              : assessment.signalStateAssessmentGroup.map((group) => {
                  const percentRed =
                    group.redEvents /
                    Math.min(group.greenEvents + group.yellowEvents + group.redEvents, 1);
                  return (
                    <Typography color="textPrimary" variant="h5">
                      {`${group.signalGroup}: ${percentRed.toFixed(2)}%`}
                    </Typography>
                  );
                })}
          </Grid>
          <Grid item>
            <NextLink href={`/map`} passHref>
              <IconButton component="a">
                <MapRoundedIcon
                  sx={{
                    height: 56,
                    width: 56,
                  }}
                  fontSize="small"
                />
              </IconButton>
            </NextLink>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};
