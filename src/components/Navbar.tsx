
import { Search, Bell, User } from "lucide-react";
import { SidebarTrigger } from "@/components/ui/sidebar";

export function Navbar() {
  return (
    <nav className="fixed top-0 left-0 right-0 bg-zinc-900 border-b border-zinc-800 h-16 z-50">
      <div className="h-full px-4 flex items-center justify-between">
        <div className="flex items-center gap-4">
          <SidebarTrigger className="text-zinc-400 hover:text-white" />
          <div className="text-xl font-bold text-white">BlogPlatform</div>
        </div>
        
        <div className="flex-1 max-w-xl mx-8">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-zinc-400" size={18} />
            <input
              type="text"
              placeholder="Search posts, users, tags..."
              className="w-full bg-zinc-800 border border-zinc-700 rounded-lg py-2 pl-10 pr-4 text-white placeholder-zinc-400 focus:outline-none focus:border-blue-500 focus:ring-1 focus:ring-blue-500"
            />
          </div>
        </div>

        <div className="flex items-center gap-4">
          <button className="relative p-2 text-zinc-400 hover:text-white transition-colors">
            <Bell size={20} />
            <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">
              3
            </span>
          </button>
          
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center">
              <User size={16} className="text-white" />
            </div>
            <div className="hidden sm:block">
              <div className="text-white text-sm font-medium">John Doe</div>
              <div className="text-zinc-400 text-xs">Lv. 4</div>
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
}
