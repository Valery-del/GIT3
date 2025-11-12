package main.java.com.analyzer.parser;

import com.analyzer.model.Commit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GitLogParser {
    public List<Commit> parse(String repoPath) {
        List<Commit> commits = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("git", "-C", repoPath, "log", "--pretty=format:%h|%an|%s");
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    commits.add(new Commit(parts[0], parts[1], parts[2]));
                }
            }
            p.waitFor();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return commits;
    }
}