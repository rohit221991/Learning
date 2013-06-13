

import java.util.ArrayList;
import java.util.Random;

public class kMeans {

  int iterations;
	int numOfClusters;

	// default constructor : iteration =50 numOfClusters=2
	public kMeans() {
		iterations = 50;
		numOfClusters = 2;
	}

	public kMeans(int numOfIteration, int numOfclusters) {
		this.iterations = numOfIteration;
		this.numOfClusters = numOfclusters;

	}

	// method to get clusters

	clusters[] getClusters(point[] data) {
		
		point[] centers = getKmeanppCentres(data);
		clusters[] clusters = reallocation(centers, data);

		for (int i = 0; i < iterations; i++) {
			centers = getCenters(clusters);
			clusters = reallocation(centers, data);
		}

		return clusters;

	}

	// get distance
	int distance(point x, point y) {
		return (int) Math.sqrt(((x.x - y.x) * (x.x - y.x) + (x.y - y.y)
				* (x.y - y.y)));
	}

	// get square of distance
	int distancesq(point x, point y) {
		return (int) ((x.x - y.x) * (x.x - y.x) + (x.y - y.y) * (x.y - y.y));
	}

	// regrouping of points
	clusters[] reallocation(point[] centers, point[] data) {

		clusters[] clus = new clusters[numOfClusters];
		for (int i = 0; i < numOfClusters; i++)
			clus[i] = new clusters();

		for (int i = 0; i < data.length; i++) {
			int dis = Integer.MAX_VALUE;
			int ind = 0;
			int temp = 0;
			for (int j = 0; j < centers.length; j++) {
				temp = distance(data[i], centers[j]);
				if (temp < dis) {
					dis = temp;
					ind = j;
				}
			}

			clus[ind].clus.add(data[i]);

		}
		return clus;

	}

	// get centers from the clusters
	point[] getCenters(clusters[] arr) {
		point[] centers = new point[numOfClusters];

		for (int i = 0; i < numOfClusters; i++) {

			point temp = new point(0, 0);
			for (int j = 0; j < arr[i].clus.size(); j++) {
				temp.x += arr[i].clus.get(j).x;
				temp.y += arr[i].clus.get(j).y;
			}

			if (arr[i].clus.size() > 0) {
				temp.x = temp.x / (arr[i].clus.size());
				temp.y = temp.y / (arr[i].clus.size());
				centers[i] = new point(temp.x, temp.y);
			}

		}

		return centers;
	}

	// basic KMeans Initialization
	point[] getRandCentres(point[] data) {
		point[] centers = new point[numOfClusters];
		for (int i = 0; i < numOfClusters; i++) {
			centers[i] = data[(int) Math.random() * numOfClusters];
		}

		return centers;
	}

	// ElAgha Initialization
	point[] getRandomCentres(point[] data) {
		int ymin = Integer.MAX_VALUE, ymax = Integer.MIN_VALUE, xmin = Integer.MAX_VALUE, xmax = Integer.MIN_VALUE;
		point[] centers = new point[numOfClusters];
		Random rand = new Random();
		for (int i = 0; i < data.length; i++) {
			if (ymax < data[i].y)
				ymax = data[i].y;
			if (ymin > data[i].y)
				ymin = data[i].y;
			if (xmax < data[i].x)
				xmax = data[i].x;
			if (xmin > data[i].x)
				xmin = data[i].x;
		}

		int xw = (xmax - xmin) / numOfClusters;
		int yw = (ymax - ymin) / numOfClusters;
		int xc = xmin;
		int yc = ymin;

		point[][] arr = new point[numOfClusters][numOfClusters];
		for (int n = 0; n < numOfClusters; n++) {
			for (int i = 0; i < numOfClusters; i++) {
				if (i == n) {
					centers[i] = new point(0, 0);
					centers[i].x = (int) (xc + Math.random() * (xw) - Math
							.random() * (xw / 2));
					centers[i].y = (int) (yc + Math.random() * (yw) - Math
							.random() * (yw / 2));

				}
				xmin += xw;

			}
			xc = xmin;
			yc += yw;
		}

		return centers;

	}

	// KMean plus plus initialization
	point[] getKmeanppCentres(point[] data) {
		Random rand = new Random();
		point[] centers = new point[numOfClusters];
		for (int i = 0; i < numOfClusters; i++) {
			centers[i] = new point(0, 0);
		}

		centers[0] = data[(int) Math.random() * numOfClusters];

		for (int k = 1; k < numOfClusters; k++) {
			int[] dis = new int[data.length];
			long sum = 0;
			for (int i = 0; i < data.length; i++) {
				int min = Integer.MAX_VALUE;
				int ind = 0;
				for (int j = 0; j < k; j++) {
					int temp = distancesq(centers[j], data[i]);
					if (temp < min) {
						min = temp;
						ind = j;
					}

				}
				dis[i] = min;
				sum += min;

			}
			sum = (long) (Math.random() * sum);
			for (int l = 0; l < data.length; l++) {
				if ((sum -= dis[l]) > 0)
					continue;
				centers[k] = data[l];
				break;
			}

		}
		return centers;

	}

}

class clusters {

	ArrayList<point> clus = new ArrayList<point>();

	public clusters(ArrayList<point> arr) {
		clus = arr;
	}

	public clusters() {
		clus = new ArrayList<point>();
	}

}

class point {
	public point(int i, int j) {
		x = i;
		y = j;
	}

	int x = 0;
	int y = 0;

}
