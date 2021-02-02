boolean threePartition(int arr[]) {
		if (arr == null) return false;
		int sum = Arrays.stream(arr).sum();
		if (sum % 3 != 0) return false;
		int n = arr.length;
		boolean dp[][][] = new boolean[n+1][sum = sum / 3 + 1][sum];
		for (int i = 0; i < n+1; i++) {
			for (int j = 0; j < sum; j++) {
				for (int k = 0; k < sum; k++) {
					if (i == 0 ) dp[i][j][k] = false;  //base case - we can't build any sum with zero elements
					else if (j == 0 || k == 0) dp[i][j][k] = true;  //base case
		/* we either take the element in the first, the second or the third subset while we actually only consider the second and third subset,
		since if their elements sum up to A/3, this will also be the case for the remaining elements that then constitute the first subset */
					else  dp[i][j][k] = j >= arr[i-1] && k >= arr[i-1] ? dp[i - 1][j - arr[i-1]][k] || dp[i - 1][j][k - arr[i-1]] 
						|| dp[i - 1][j][k] : j >= arr[i-1] ? dp[i - 1][j - arr[i-1]][k] || dp[i - 1][j][k]
						 : k >= arr[i-1] ? dp[i - 1][j][k - arr[i-1]] || dp[i - 1][j][k] : dp[i - 1][j][k];
				}
			}
		}
		return dp[n][sum-1][sum-1];
	}
