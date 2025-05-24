
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";

interface VersionDropdownProps {
  selectedVersion: string;
  onVersionChange: (version: string) => void;
}

export function VersionDropdown({ selectedVersion, onVersionChange }: VersionDropdownProps) {
  const versions = ["v1", "v2", "v3"];

  return (
    <div className="flex items-center gap-2">
      <span className="text-zinc-400 text-sm">Version:</span>
      <Select value={selectedVersion} onValueChange={onVersionChange}>
        <SelectTrigger className="w-20 bg-zinc-800 border-zinc-700 text-white">
          <SelectValue />
        </SelectTrigger>
        <SelectContent className="bg-zinc-800 border-zinc-700">
          {versions.map((version) => (
            <SelectItem key={version} value={version} className="text-white hover:bg-zinc-700">
              {version}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
    </div>
  );
}
