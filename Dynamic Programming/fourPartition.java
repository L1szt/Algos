public static boolean fourPartition(int arr[]) {
		int sum = Arrays.stream(arr).sum();
		if (sum % 4 != 0) return false;
		boolean dp[][][][] = new boolean[arr.length+1][sum = sum/4 + 1][sum][sum];
		for (int i =0; i < arr.length+1; i++) {
			for (int j =0; j < sum; j++) {
				for (int k =0; k < sum; k++) {
					for (int v = 0; v < sum; v++) {
						if (i == 0) dp[i][j][k][v] = false;
						else if (j == 0 || k == 0 || v == 0) dp[i][j][k][v] = true;
						else {
							if (k >= arr[i-1] && j >= arr[i-1] && v >= arr[i-1]) dp[i][j][k][v] = dp[i-1][j-arr[i-1]][k][v] 
							|| dp[i-1][j][k-arr[i-1]][v] || dp[i-1][j][k][v-arr[i-1]] || dp[i-1][j][k][v];
							else if (k >= arr[i-1] && j >= arr[i-1]) {
								dp[i][j][k][v] = dp[i-1][j-arr[i-1]][k][v] || dp[i-1][j][k-arr[i-1]][v] ||dp[i-1][j][k][v];
							} else if (k >= arr[i-1] && v >= arr[i-1]) {
								dp[i][j][k][v] = dp[i-1][j][k-arr[i-1]][v]  || dp[i-1][j][k][v-arr[i-1]] || dp[i-1][j][k][v];
							} else if (j >= arr[i-1]&& v >= arr[i-1]) {
								dp[i][j][k][v] = dp[i-1][j-arr[i-1]][k][v] || dp[i-1][j][k][v-arr[i-1]] || dp[i-1][j][k][v];
							} else if (k >= arr[i-1]) {
								dp[i][j][k][v] = dp[i-1][j][k-arr[i-1]][v]  || dp[i-1][j][k][v];
							} else if (v >= arr[i-1]) {
								dp[i][j][k][v] = dp[i-1][j][k][v-arr[i-1]] || dp[i-1][j][k][v];
							} else if (j >= arr[i-1]) {
								dp[i][j][k][v] = dp[i-1][j-arr[i-1]][k][v]|| dp[i-1][j][k][v];
							} else dp[i][j][k][v] = dp[i-1][j][k][v];
						}
					}
				}
			}
		}
		return dp[arr.length][sum-1][sum-1][sum-1];
	}
