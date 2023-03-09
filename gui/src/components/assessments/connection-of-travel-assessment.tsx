import { Avatar, Box, Card, CardContent, Grid, Typography, IconButton } from "@mui/material";
import MapRoundedIcon from "@mui/icons-material/MapRounded";
import React from "react";
import NextLink from "next/link";

export const ConnectionOfTravelAssessmentCard = (props: {
  assessment: ConnectionOfTravelAssessment | null;
  small: Boolean;
}) => {
  const { assessment } = props;

  return (
    <Card sx={{ height: "100%" }}>
      <CardContent>
        <Grid container spacing={3} sx={{ justifyContent: "space-between" }}>
          <Grid item>
            <Typography color="textSecondary" gutterBottom variant="overline">
              Connection of Travel Assessment
            </Typography>
            {assessment == null
              ? ""
              : assessment.connectionOfTravelAssessmentGroups.map((group) => {
                  return (
                    <Typography color="textPrimary" variant="h5">
                      {`${group.ingressLaneID}/${group.egressLaneID}: ${group.eventCount}%`}
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
