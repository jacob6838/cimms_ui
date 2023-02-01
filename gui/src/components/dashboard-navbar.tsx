import { useRef, useState } from "react";
import PropTypes from "prop-types";
import styled from "@emotion/styled";
import {
  AppBar,
  Avatar,
  Badge,
  Box,
  IconButton,
  Toolbar,
  Tooltip,
  Theme,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import MapRoundedIcon from "@mui/icons-material/MapRounded";
import { Bell as BellIcon } from "../icons/bell";
import { UserCircle as UserCircleIcon } from "../icons/user-circle";
import { Users as UsersIcon } from "../icons/users";
import { AccountPopover } from "./account-popover";
import React from "react";

const DashboardNavbarRoot = styled(AppBar)(({ theme }: { theme: Theme }) => ({
  backgroundColor: theme.palette.background.paper,
  boxShadow: theme.shadows[3],
}));

interface Props {
  onSidebarOpen: () => void;
  intersections: IntersectionSummary[];
  intersectionId: number;
  setIntersectionId: (val: number) => void;
}

export const DashboardNavbar = (props: Props) => {
  const { onSidebarOpen, intersections, intersectionId, setIntersectionId, ...other } = props;
  const settingsRef = useRef(null);
  const [openAccountPopover, setOpenAccountPopover] = useState(false);

  return (
    <>
      <DashboardNavbarRoot
        sx={{
          left: {
            lg: 280,
          },
          width: {
            lg: "calc(100% - 280px)",
          },
        }}
        {...other}
      >
        <Toolbar
          disableGutters
          sx={{
            minHeight: 64,
            left: 0,
            px: 2,
          }}
        >
          <IconButton
            onClick={onSidebarOpen}
            sx={{
              display: {
                xs: "inline-flex",
                lg: "none",
              },
            }}
          >
            <MenuIcon fontSize="small" />
          </IconButton>
          <Tooltip title="Select Intersection">
            <FormControl sx={{ width: 400, ml: 1, mt: 1 }} variant="filled">
              <InputLabel id="demo-simple-select-label">Intersection ID</InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={intersectionId}
                label="Age"
                onChange={(e) => setIntersectionId(e.target.value as number)}
              >
                {intersections.map((intersection) => {
                  return <MenuItem value={intersection.id}>{intersection.id}</MenuItem>;
                })}
              </Select>
            </FormControl>
          </Tooltip>
          <IconButton sx={{ ml: 0.5 }} onClick={() => {}}>
            <MapRoundedIcon fontSize="large" />
          </IconButton>
          <Box sx={{ flexGrow: 1 }} />
          <Tooltip title="Contacts">
            <IconButton sx={{ ml: 1 }}>
              <UsersIcon fontSize="small" />
            </IconButton>
          </Tooltip>
          <Tooltip title="Notifications">
            <IconButton sx={{ ml: 1 }}>
              <Badge badgeContent={4} color="primary" variant="dot">
                <BellIcon fontSize="small" />
              </Badge>
            </IconButton>
          </Tooltip>
          <Avatar
            onClick={() => setOpenAccountPopover(true)}
            ref={settingsRef}
            sx={{
              cursor: "pointer",
              height: 40,
              width: 40,
              ml: 1,
            }}
            src="/static/images/avatars/avatar_1.png"
          >
            <UserCircleIcon fontSize="small" />
          </Avatar>
        </Toolbar>
      </DashboardNavbarRoot>
      <AccountPopover
        anchorEl={settingsRef.current}
        open={openAccountPopover}
        onClose={() => setOpenAccountPopover(false)}
      />
    </>
  );
};

DashboardNavbar.propTypes = {};
