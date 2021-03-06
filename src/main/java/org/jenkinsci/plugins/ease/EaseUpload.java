package org.jenkinsci.plugins.ease;

import java.util.LinkedHashMap;
import java.util.Map;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.FilePath;
import hudson.util.Function1;

public class EaseUpload {
    private String url;
    private String appId;
    private String filename;
    private String username;
    private String password;
    private String metadataAssignment;
    private transient  FilePath filePath;

    @DataBoundConstructor
    public EaseUpload(String _url,
                      String _username,
                      String _password,
                      String _appId,
                      String _filename,
                      String _metadataAssignment) {
        this.url = Utils.trim(_url);
        this.username = Utils.trim(_username);
        this.password = _password;
        this.appId = Utils.trim(_appId);
        this.filename = Utils.trim(_filename);
        this.metadataAssignment = Utils.trim(_metadataAssignment);
    }

    public EaseUpload derive(EaseUpload additionalUpload) {
        return new EaseUpload(
                Utils.override(additionalUpload.url, url),
                Utils.override(additionalUpload.username, username),
                Utils.override(additionalUpload.password, password),
                additionalUpload.getAppId(),
                additionalUpload.getFilename(),
                Utils.override(additionalUpload.metadataAssignment, metadataAssignment));
    }

    public void setFilePath(FilePath filePath) {
        this.filePath = filePath;
    }

    public FilePath getFilePath() {
        return filePath;
    }

    public String getUrl() {
        return url;
    }

    public String getAppId() {
        return appId;
    }

    public String getFilename() {
        return filename;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMetadataAssignment() {
        return metadataAssignment;
    }

    public boolean checkOk() {
        return !(Utils.isEmptyString(appId)
                || Utils.isEmptyString(url)
                || Utils.isEmptyString(filename));
    }

    public void expand(Function1<String, String> expandVars) {
        url = expandVars.call(url);
        appId = expandVars.call(appId);
        filename = expandVars.call(filename);
        username = expandVars.call(username);
        Map<String, String> map = Utils.parseAssignmentMap(metadataAssignment);
        map = expandValuesOnly(map, expandVars);
        metadataAssignment = Utils.outAssignmentMap(map);
    }

    private Map<String, String> expandValuesOnly(Map<String, String> map,
                                                 Function1<String, String> expandVars) {
        Map<String, String> resultMap = new LinkedHashMap<>(map.size());
        for (String key : map.keySet()) {
            String val = map.get(key);
            String expandedVal = expandVars.call(val);
            resultMap.put(key, expandedVal);
        }

        return resultMap;
    }
}
