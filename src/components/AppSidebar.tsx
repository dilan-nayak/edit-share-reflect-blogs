
import { useState } from "react";
import { Plus, User, Eye, Search, LayoutDashboard } from "lucide-react";
import { NavLink, useLocation, useNavigate } from "react-router-dom";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarTrigger,
  useSidebar,
} from "@/components/ui/sidebar";

const menuItems = [
  { title: "My Feed", url: "/", icon: User },
  { title: "Following", url: "/following", icon: User },
  { title: "Explore", url: "/explore", icon: Search },
  { title: "History", url: "/history", icon: Eye },
  { title: "Dashboard", url: "/dashboard", icon: LayoutDashboard },
];

const feeds = ["Java", "Frontend", "Backend", "AI", "React", "Node.js"];
const networkItems = ["Find Squads", "AI Tools", "My Network"];

export function AppSidebar() {
  const { state } = useSidebar();
  const location = useLocation();
  const navigate = useNavigate();
  const currentPath = location.pathname;
  const collapsed = state === "collapsed";

  const isActive = (path: string) => currentPath === path;

  const handleNewPost = () => {
    navigate("/new-post");
  };

  const handleFeedClick = (feed: string) => {
    alert(`${feed} feed coming soon!`);
  };

  const handleNetworkClick = (item: string) => {
    alert(`${item} feature coming soon!`);
  };

  return (
    <Sidebar className={`${collapsed ? "w-14" : "w-64"} bg-zinc-900 border-r border-zinc-800 fixed left-0 top-16 h-[calc(100vh-4rem)]`} collapsible="icon">
      <SidebarTrigger className="m-2 self-end text-zinc-400 hover:text-white" />
      
      <SidebarContent className="p-4 bg-zinc-900">
        {/* New Post Button */}
        <div className="mb-6">
          <button 
            onClick={handleNewPost}
            className="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 px-4 rounded-lg flex items-center gap-2 transition-colors"
          >
            <Plus size={18} />
            {!collapsed && <span>New Post</span>}
          </button>
        </div>

        {/* Main Menu */}
        <SidebarGroup>
          <SidebarGroupContent>
            <SidebarMenu>
              {menuItems.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton asChild>
                    <NavLink 
                      to={item.url} 
                      className={`flex items-center gap-3 px-3 py-2 rounded-lg transition-colors ${
                        isActive(item.url) 
                          ? "bg-blue-600 text-white" 
                          : "text-zinc-400 hover:text-white hover:bg-zinc-800"
                      }`}
                    >
                      <item.icon size={18} />
                      {!collapsed && <span>{item.title}</span>}
                    </NavLink>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>

        {/* Feeds Section */}
        {!collapsed && (
          <SidebarGroup className="mt-6">
            <SidebarGroupLabel className="text-zinc-500 text-sm font-medium mb-3">
              Feeds
            </SidebarGroupLabel>
            <SidebarGroupContent>
              <SidebarMenu>
                {feeds.map((feed) => (
                  <SidebarMenuItem key={feed}>
                    <SidebarMenuButton asChild>
                      <button 
                        onClick={() => handleFeedClick(feed)}
                        className="w-full text-left text-zinc-400 hover:text-white hover:bg-zinc-800 px-3 py-2 rounded-lg transition-colors text-sm"
                      >
                        # {feed}
                      </button>
                    </SidebarMenuButton>
                  </SidebarMenuItem>
                ))}
              </SidebarMenu>
            </SidebarGroupContent>
          </SidebarGroup>
        )}

        {/* Network Section */}
        {!collapsed && (
          <SidebarGroup className="mt-6">
            <SidebarGroupLabel className="text-zinc-500 text-sm font-medium mb-3">
              Network
            </SidebarGroupLabel>
            <SidebarGroupContent>
              <SidebarMenu>
                {networkItems.map((item) => (
                  <SidebarMenuItem key={item}>
                    <SidebarMenuButton asChild>
                      <button 
                        onClick={() => handleNetworkClick(item)}
                        className="w-full text-left text-zinc-400 hover:text-white hover:bg-zinc-800 px-3 py-2 rounded-lg transition-colors text-sm"
                      >
                        {item}
                      </button>
                    </SidebarMenuButton>
                  </SidebarMenuItem>
                ))}
              </SidebarMenu>
            </SidebarGroupContent>
          </SidebarGroup>
        )}
      </SidebarContent>
    </Sidebar>
  );
}
