package org.harunjaganjac.example.response;

public final class ProjectResponse extends BaseResponse {
    private final String projectId;

    public ProjectResponse(String message, boolean success, String projectId) {
        super(message, success);
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }

}
