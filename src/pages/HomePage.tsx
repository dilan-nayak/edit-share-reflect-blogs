
import { BlogCard } from "@/components/BlogCard";
import { mockBlogPosts } from "@/data/mockData";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export function HomePage() {
  const [posts] = useState(mockBlogPosts);
  const navigate = useNavigate();

  const handleLoadMore = () => {
    alert("Load More feature coming soon!");
  };

  const handleBlogClick = (id: string) => {
    navigate(`/blog/${id}`);
  };

  return (
    <div className="min-h-screen bg-zinc-950 p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-white mb-2">My Feed</h1>
          <p className="text-zinc-400">Discover the latest posts from developers you follow</p>
        </div>

        {/* Blog Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
          {posts.map((post) => (
            <div key={post.id} onClick={() => handleBlogClick(post.id)} className="cursor-pointer">
              <BlogCard post={post} />
            </div>
          ))}
        </div>

        {/* Load More */}
        <div className="mt-12 text-center">
          <button 
            onClick={handleLoadMore}
            className="bg-zinc-800 hover:bg-zinc-700 text-white px-6 py-3 rounded-lg transition-colors"
          >
            Load More Posts
          </button>
        </div>
      </div>
    </div>
  );
}
