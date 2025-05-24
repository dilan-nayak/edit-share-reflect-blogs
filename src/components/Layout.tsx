
import { SidebarProvider } from "@/components/ui/sidebar";
import { AppSidebar } from "./AppSidebar";
import { Navbar } from "./Navbar";

interface LayoutProps {
  children: React.ReactNode;
}

export function Layout({ children }: LayoutProps) {
  return (
    <SidebarProvider>
      <div className="min-h-screen bg-zinc-950 text-white w-full">
        <Navbar />
        <div className="flex w-full">
          <AppSidebar />
          <main className="flex-1 pt-16">
            {children}
          </main>
        </div>
      </div>
    </SidebarProvider>
  );
}
