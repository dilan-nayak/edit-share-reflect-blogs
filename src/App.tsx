
import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Index from "./pages/Index";
import BlogDetail from "./pages/BlogDetail";
import NewPost from "./pages/NewPost";
import EditBlog from "./pages/EditBlog";
import Dashboard from "./pages/Dashboard";
import NotFound from "./pages/NotFound";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Index />} />
          <Route path="/new-post" element={<NewPost />} />
          <Route path="/blog/:id" element={<BlogDetail />} />
          <Route path="/blog/:id/edit" element={<EditBlog />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/following" element={<Index />} />
          <Route path="/explore" element={<Index />} />
          <Route path="/history" element={<Index />} />
          {/* ADD ALL CUSTOM ROUTES ABOVE THE CATCH-ALL "*" ROUTE */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
