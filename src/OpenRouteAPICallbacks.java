public interface OpenRouteAPICallbacks {

    void OnSummarySuccess(Summary summary);
    void OnDriveMatrixSuccess(Matrix matrix);
    void OnFootMatrixSuccess(Matrix matrix);
}
