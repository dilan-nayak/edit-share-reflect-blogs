
import { useState } from "react";
import { Layout } from "@/components/Layout";
import { Check, X, Eye, User } from "lucide-react";

interface EditRequest {
  id: string;
  blogTitle: string;
  editorName: string;
  reason: string;
  proposedChanges: string;
  status: "pending" | "accepted" | "rejected";
  submittedAt: string;
}

const mockEditRequests: EditRequest[] = [
  {
    id: "1",
    blogTitle: "Building Scalable React Applications with TypeScript",
    editorName: "Alice Johnson",
    reason: "Fixed typos and improved code examples",
    proposedChanges: "Corrected several grammatical errors and updated the TypeScript examples to use more modern syntax...",
    status: "pending",
    submittedAt: "2 hours ago"
  },
  {
    id: "2",
    blogTitle: "The Future of AI in Software Development",
    editorName: "Bob Smith",
    reason: "Added recent AI developments and updated statistics",
    proposedChanges: "Added section about GPT-4 and updated industry statistics with 2024 data...",
    status: "pending",
    submittedAt: "5 hours ago"
  },
  {
    id: "3",
    blogTitle: "Microservices vs Monoliths: Choosing the Right Architecture",
    editorName: "Carol Davis",
    reason: "Enhanced decision framework section",
    proposedChanges: "Expanded the decision framework with more real-world examples and case studies...",
    status: "accepted",
    submittedAt: "1 day ago"
  }
];

export default function Dashboard() {
  const [editRequests, setEditRequests] = useState<EditRequest[]>(mockEditRequests);

  const handleAccept = (id: string) => {
    setEditRequests(prev => 
      prev.map(request => 
        request.id === id 
          ? { ...request, status: "accepted" as const }
          : request
      )
    );
    console.log(`Accepted edit request ${id}`);
  };

  const handleReject = (id: string) => {
    setEditRequests(prev => 
      prev.map(request => 
        request.id === id 
          ? { ...request, status: "rejected" as const }
          : request
      )
    );
    console.log(`Rejected edit request ${id}`);
  };

  const pendingRequests = editRequests.filter(req => req.status === "pending");
  const processedRequests = editRequests.filter(req => req.status !== "pending");

  return (
    <Layout>
      <div className="min-h-screen bg-zinc-950 p-6">
        <div className="max-w-6xl mx-auto">
          {/* Header */}
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-white mb-2">Author Dashboard</h1>
            <p className="text-zinc-400">Manage edit proposals for your blog posts</p>
          </div>

          {/* Pending Requests */}
          <div className="mb-8">
            <h2 className="text-xl font-semibold text-white mb-4">
              Pending Edit Requests ({pendingRequests.length})
            </h2>
            
            {pendingRequests.length === 0 ? (
              <div className="bg-zinc-900 rounded-lg p-8 text-center">
                <p className="text-zinc-400">No pending edit requests</p>
              </div>
            ) : (
              <div className="space-y-4">
                {pendingRequests.map((request) => (
                  <div key={request.id} className="bg-zinc-900 rounded-lg p-6">
                    <div className="flex items-start justify-between mb-4">
                      <div className="flex-1">
                        <h3 className="text-lg font-medium text-white mb-2">
                          {request.blogTitle}
                        </h3>
                        <div className="flex items-center gap-4 text-sm text-zinc-400 mb-3">
                          <div className="flex items-center gap-2">
                            <User size={16} />
                            <span>{request.editorName}</span>
                          </div>
                          <span>•</span>
                          <span>{request.submittedAt}</span>
                        </div>
                        <div className="mb-3">
                          <p className="text-zinc-300 font-medium mb-1">Reason:</p>
                          <p className="text-zinc-400">{request.reason}</p>
                        </div>
                        <div>
                          <p className="text-zinc-300 font-medium mb-1">Proposed Changes:</p>
                          <p className="text-zinc-400 line-clamp-2">{request.proposedChanges}</p>
                        </div>
                      </div>
                    </div>
                    
                    <div className="flex items-center gap-3">
                      <button
                        onClick={() => handleAccept(request.id)}
                        className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg flex items-center gap-2 transition-colors"
                      >
                        <Check size={16} />
                        Accept
                      </button>
                      <button
                        onClick={() => handleReject(request.id)}
                        className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-lg flex items-center gap-2 transition-colors"
                      >
                        <X size={16} />
                        Reject
                      </button>
                      <button className="bg-zinc-800 hover:bg-zinc-700 text-white px-4 py-2 rounded-lg flex items-center gap-2 transition-colors">
                        <Eye size={16} />
                        View Details
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* Processed Requests */}
          {processedRequests.length > 0 && (
            <div>
              <h2 className="text-xl font-semibold text-white mb-4">
                Recent Activity ({processedRequests.length})
              </h2>
              
              <div className="space-y-4">
                {processedRequests.map((request) => (
                  <div key={request.id} className="bg-zinc-900 rounded-lg p-6 opacity-75">
                    <div className="flex items-start justify-between mb-4">
                      <div className="flex-1">
                        <h3 className="text-lg font-medium text-white mb-2">
                          {request.blogTitle}
                        </h3>
                        <div className="flex items-center gap-4 text-sm text-zinc-400 mb-3">
                          <div className="flex items-center gap-2">
                            <User size={16} />
                            <span>{request.editorName}</span>
                          </div>
                          <span>•</span>
                          <span>{request.submittedAt}</span>
                          <span>•</span>
                          <span className={`capitalize font-medium ${
                            request.status === "accepted" ? "text-green-400" : "text-red-400"
                          }`}>
                            {request.status}
                          </span>
                        </div>
                        <div className="mb-3">
                          <p className="text-zinc-300 font-medium mb-1">Reason:</p>
                          <p className="text-zinc-400">{request.reason}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </Layout>
  );
}
