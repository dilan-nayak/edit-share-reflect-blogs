
import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Layout } from "@/components/Layout";
import { mockBlogPosts } from "@/data/mockData";
import { VersionDropdown } from "@/components/VersionDropdown";
import { ArrowLeft, Edit, Eye, MessageCircle, ThumbsUp } from "lucide-react";

export default function BlogDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [selectedVersion, setSelectedVersion] = useState("v1");
  
  const blog = mockBlogPosts.find(post => post.id === id);
  
  if (!blog) {
    return (
      <Layout>
        <div className="min-h-screen bg-zinc-950 p-6">
          <div className="max-w-4xl mx-auto">
            <h1 className="text-2xl font-bold text-white">Blog not found</h1>
          </div>
        </div>
      </Layout>
    );
  }

  const handleProposeEdit = () => {
    navigate(`/blog/${id}/edit`);
  };

  const handleBack = () => {
    navigate("/");
  };

  return (
    <Layout>
      <div className="min-h-screen bg-zinc-950 p-6">
        <div className="max-w-4xl mx-auto">
          {/* Header */}
          <div className="flex items-center gap-4 mb-6">
            <button
              onClick={handleBack}
              className="p-2 text-zinc-400 hover:text-white hover:bg-zinc-800 rounded-lg transition-colors"
            >
              <ArrowLeft size={20} />
            </button>
            <div className="flex-1">
              <h1 className="text-3xl font-bold text-white mb-2">{blog.title}</h1>
              <div className="flex items-center gap-4 text-zinc-400 text-sm">
                <span>by {blog.author.name}</span>
                <span>•</span>
                <span>{blog.date}</span>
                <span>•</span>
                <span>Level {blog.author.level}</span>
              </div>
            </div>
            <VersionDropdown 
              selectedVersion={selectedVersion}
              onVersionChange={setSelectedVersion}
            />
          </div>

          {/* Blog Image */}
          {blog.image && (
            <div className="mb-8">
              <img 
                src={blog.image} 
                alt={blog.title}
                className="w-full h-64 object-cover rounded-lg"
              />
            </div>
          )}

          {/* Blog Content */}
          <div className="bg-zinc-900 rounded-lg p-8 mb-8">
            <div className="prose prose-invert max-w-none">
              <p className="text-zinc-300 leading-relaxed text-lg">
                {blog.content}
              </p>
            </div>
          </div>

          {/* Tags */}
          {blog.tags && (
            <div className="flex flex-wrap gap-2 mb-8">
              {blog.tags.map((tag) => (
                <span 
                  key={tag}
                  className="bg-zinc-800 text-zinc-300 px-3 py-1 rounded-full text-sm"
                >
                  {tag}
                </span>
              ))}
            </div>
          )}

          {/* Stats and Actions */}
          <div className="flex items-center justify-between bg-zinc-900 rounded-lg p-6">
            <div className="flex items-center gap-6">
              <div className="flex items-center gap-2 text-zinc-400">
                <ThumbsUp size={18} />
                <span>{blog.stats.likes}</span>
              </div>
              <div className="flex items-center gap-2 text-zinc-400">
                <MessageCircle size={18} />
                <span>{blog.stats.comments}</span>
              </div>
              <div className="flex items-center gap-2 text-zinc-400">
                <Eye size={18} />
                <span>{blog.stats.views}</span>
              </div>
              {blog.proposedEdits && (
                <div className="text-blue-400">
                  {blog.proposedEdits} proposed edits
                </div>
              )}
            </div>
            
            <button
              onClick={handleProposeEdit}
              className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg flex items-center gap-2 transition-colors"
            >
              <Edit size={18} />
              Propose Edit
            </button>
          </div>
        </div>
      </div>
    </Layout>
  );
}
