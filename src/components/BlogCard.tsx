
import { MessageSquare, ThumbsUp, Eye, User } from "lucide-react";

interface BlogPost {
  id: string;
  title: string;
  author: {
    name: string;
    avatar: string;
    level: number;
  };
  content: string;
  image?: string;
  date: string;
  tags: string[];
  stats: {
    comments: number;
    likes: number;
    views: number;
  };
  proposedEdits?: number;
}

interface BlogCardProps {
  post: BlogPost;
}

export function BlogCard({ post }: BlogCardProps) {
  return (
    <article className="bg-zinc-900 border border-zinc-800 rounded-xl overflow-hidden hover:border-zinc-700 transition-all duration-200 hover:shadow-xl hover:shadow-black/20">
      {/* Author Header */}
      <div className="p-4 pb-3">
        <div className="flex items-center gap-3">
          {post.author.avatar ? (
            <img 
              src={post.author.avatar} 
              alt={post.author.name}
              className="w-10 h-10 rounded-full object-cover"
            />
          ) : (
            <div className="w-10 h-10 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center">
              <User size={18} className="text-white" />
            </div>
          )}
          <div>
            <div className="font-medium text-white">{post.author.name}</div>
            <div className="text-zinc-400 text-sm flex items-center gap-2">
              <span>{post.date}</span>
              <span>•</span>
              <span>Lv. {post.author.level}</span>
            </div>
          </div>
        </div>
      </div>

      {/* Content */}
      <div className="px-4 pb-3">
        <h2 className="text-xl font-bold text-white mb-3 leading-tight hover:text-blue-400 cursor-pointer transition-colors">
          {post.title}
        </h2>
        
        <p className="text-zinc-300 text-sm leading-relaxed line-clamp-3">
          {post.content}
        </p>
      </div>

      {/* Image */}
      {post.image && (
        <div className="px-4 pb-3">
          <img 
            src={post.image} 
            alt="Post preview"
            className="w-full h-48 object-cover rounded-lg"
          />
        </div>
      )}

      {/* Tags */}
      {post.tags.length > 0 && (
        <div className="px-4 pb-3">
          <div className="flex flex-wrap gap-2">
            {post.tags.map((tag) => (
              <span 
                key={tag}
                className="bg-zinc-800 text-zinc-300 px-2 py-1 rounded-md text-xs hover:bg-zinc-700 cursor-pointer transition-colors"
              >
                #{tag}
              </span>
            ))}
          </div>
        </div>
      )}

      {/* Proposed Edits Banner */}
      {post.proposedEdits && post.proposedEdits > 0 && (
        <div className="mx-4 mb-3 bg-amber-900/30 border border-amber-700/50 rounded-lg p-3">
          <div className="text-amber-400 text-sm font-medium">
            {post.proposedEdits} proposed edit{post.proposedEdits > 1 ? 's' : ''} pending review
          </div>
          <button className="text-amber-300 text-xs hover:text-amber-200 transition-colors">
            Review changes →
          </button>
        </div>
      )}

      {/* Stats Footer */}
      <div className="px-4 py-3 border-t border-zinc-800 bg-zinc-950/50">
        <div className="flex items-center justify-between text-zinc-400">
          <div className="flex items-center gap-4">
            <button className="flex items-center gap-1 hover:text-blue-400 transition-colors">
              <MessageSquare size={16} />
              <span className="text-sm">{post.stats.comments}</span>
            </button>
            
            <button className="flex items-center gap-1 hover:text-red-400 transition-colors">
              <ThumbsUp size={16} />
              <span className="text-sm">{post.stats.likes}</span>
            </button>
          </div>
          
          <div className="flex items-center gap-1 text-zinc-500">
            <Eye size={16} />
            <span className="text-sm">{post.stats.views}</span>
          </div>
        </div>
      </div>
    </article>
  );
}
