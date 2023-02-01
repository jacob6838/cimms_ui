import { useState, useEffect } from "react";
import { Box } from "@mui/material";
import { styled } from "@mui/material/styles";
import { AuthGuard } from "./auth-guard";
import { DashboardNavbar } from "./dashboard-navbar";
import { DashboardSidebar } from "./dashboard-sidebar";
import React from "react";
import { useSession, signIn, signOut } from "next-auth/react";
import MessageMonitorApi from "../apis/mm-api";

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

  useEffect(() => {
    setIntersections(
      MessageMonitorApi.getIntersections().map((intersection) => {
        return {
          id: intersection.intersectionId,
          location: [intersection.referencePoint.y!, intersection.referencePoint.x!],
        };
      })
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
