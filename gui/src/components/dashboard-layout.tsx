import { useState, useEffect } from "react";
import { Box } from "@mui/material";
import { styled } from "@mui/material/styles";
import { AuthGuard } from "./auth-guard";
import { DashboardNavbar } from "./dashboard-navbar";
import { DashboardSidebar } from "./dashboard-sidebar";
import React from "react";
import { useSession, signIn, signOut } from "next-auth/react";
import MessageMonitorApi from "../apis/mm-api";
import EventsApi from "../apis/events-api";

const DashboardLayoutRoot = styled("div")(({ theme }) => ({
  display: "flex",
  flex: "1 1 auto",
  maxWidth: "100%",
  paddingTop: 64,
  [theme.breakpoints.up("lg")]: {
    paddingLeft: 280,
  },
}));

export const DashboardLayout = (props) => {
  const { children } = props;
  const [isSidebarOpen, setSidebarOpen] = useState(true);
  const { data: session } = useSession();
  const [intersectionId, setIntersectionId] = useState(12109);
  const [selectedNotification, setSelectedNotification] = useState(null);
  const [intersections, setIntersections] = useState<IntersectionSummary[]>([]);

  const getTimeChangeDetails = async () => {
    console.log("getTimeChangeDetails");
    const details = await EventsApi.getTimeChangeDetails(
      "token",
      intersectionId,
      new Date(),
      new Date()
    );
    console.log("TimeChangeDetails", details);
  };

  useEffect(() => {
    getTimeChangeDetails();
    MessageMonitorApi.getIntersections({ token: "token" }).then((intersections) =>
      setIntersections(
        intersections.map((intersection) => {
          return {
            id: intersection.intersectionId,
            location:
              intersection.referencePoint == null
                ? null
                : [intersection.referencePoint.y!, intersection.referencePoint.x!],
          };
        })
      )
    );
  }, []);

  return (
    <AuthGuard>
      <DashboardLayoutRoot>
        <Box
          sx={{
            display: "flex",
            flex: "1 1 auto",
            flexDirection: "column",
            width: "100%",
          }}
        >
          {children}
        </Box>
      </DashboardLayoutRoot>
      <DashboardNavbar
        onSidebarOpen={() => setSidebarOpen(true)}
        intersections={intersections}
        intersectionId={intersectionId}
        setIntersectionId={setIntersectionId}
      />
      <DashboardSidebar onClose={() => setSidebarOpen(false)} open={isSidebarOpen} />
    </AuthGuard>
  );
};
