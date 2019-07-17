package main.OpenRouteAPI;

public interface OpenRouteAPICallbacks {
	void OnDriveMatrixSuccess(MatrixResponse matrixResponse);

	void OnFootMatrixSuccess(MatrixResponse matrixResponse);
}
