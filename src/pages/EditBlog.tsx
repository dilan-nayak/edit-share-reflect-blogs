
import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Layout } from "@/components/Layout";
import { mockBlogPosts } from "@/data/mockData";
import { ArrowLeft, Save } from "lucide-react";

export default function EditBlog() {
  const { id } = useParams();
  const navigate = useNavigate();
  
  const blog = mockBlogPosts.find(post => post.id === id);
  const [content, setContent] = useState(blog?.content || "");
  const [reason, setReason] = useState("");

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

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    const proposedEdit = {
      blogId: id,
      originalTitle: blog.title,
      proposedContent: content,
      reason,
      proposedBy: "John Doe",
      proposedAt: new Date().toISOString(),
      status: "pending"
    };

    console.log("Proposed edit:", proposedEdit);
    alert("Edit proposal submitted! (Check console for details)");
    navigate(`/blog/${id}`);
  };

  const handleBack = () => {
    navigate(`/blog/${id}`);
  };

  return (
    <Layout>
      <div className="min-h-screen bg-zinc-950 p-6">
        <div className="max-w-4xl mx-auto">
          {/* Header */}
          <div className="flex items-center gap-4 mb-8">
            <button
              onClick={handleBack}
              className="p-2 text-zinc-400 hover:text-white hover:bg-zinc-800 rounded-lg transition-colors"
            >
              <ArrowLeft size={20} />
            </button>
            <div>
              <h1 className="text-3xl font-bold text-white">Propose Edit</h1>
              <p className="text-zinc-400 mt-1">Editing: {blog.title}</p>
            </div>
          </div>

          {/* Form */}
          <form onSubmit={handleSubmit} className="space-y-6">
            {/* Reason Input */}
            <div>
              <label htmlFor="reason" className="block text-white text-sm font-medium mb-2">
                Reason for Edit
              </label>
              <input
                id="reason"
                type="text"
                value={reason}
                onChange={(e) => setReason(e.target.value)}
                placeholder="Why are you proposing this edit?"
                className="w-full bg-zinc-900 border border-zinc-700 rounded-lg px-4 py-3 text-white placeholder-zinc-400 focus:outline-none focus:border-blue-500 focus:ring-1 focus:ring-blue-500"
                required
              />
            </div>

            {/* Content Textarea */}
            <div>
              <label htmlFor="content" className="block text-white text-sm font-medium mb-2">
                Proposed Content
              </label>
              <textarea
                id="content"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                rows={15}
                className="w-full bg-zinc-900 border border-zinc-700 rounded-lg px-4 py-3 text-white placeholder-zinc-400 focus:outline-none focus:border-blue-500 focus:ring-1 focus:ring-blue-500 resize-y"
                required
              />
            </div>

            {/* Action Buttons */}
            <div className="flex justify-end gap-4">
              <button
                type="button"
                onClick={handleBack}
                className="bg-zinc-800 hover:bg-zinc-700 text-white px-6 py-3 rounded-lg transition-colors"
              >
                Cancel
              </button>
              <button
                type="submit"
                className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-lg flex items-center gap-2 transition-colors font-medium"
              >
                <Save size={18} />
                Submit Proposal
              </button>
            </div>
          </form>
        </div>
      </div>
    </Layout>
  );
}
