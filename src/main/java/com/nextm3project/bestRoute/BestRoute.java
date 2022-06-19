package com.nextm3project.bestRoute;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.nextm3project.models.TruckModel;

public class BestRoute {
	public static String routeCalc(String status, String location) throws URISyntaxException {
		int[][] m = new int[19][19];
		int contLineMat = 0;

		// inicio da leitura do grafo
		try (BufferedReader br = Files.newBufferedReader(
				Paths.get(ClassLoader.getSystemResource("MODELAGEM_DESAFIO_NEXT.csv").toURI()))) {

			String line = br.readLine();
			line = br.readLine();

			while (line != null) {

				String[] vect = line.split(";");
				for (int contColMat = 0; contColMat < 19; contColMat++) {
					m[contLineMat][contColMat] = Integer.parseInt(vect[contColMat]);
				}
				contLineMat++;
				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		int n = m.length;
		int[][] path = new int[n][n];

		// Modifica a matriz para encontrar o start

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (m[i][j] == 100000)
					path[i][j] = -1;
				else
					path[i][j] = i;

		shortestpath(m, path);

		TruckModel truckModel = new TruckModel();
		String statusCaminhao = status; 
		String locationCaminhao = location;
		
		Map<Integer, String> matrixMap = new HashMap<Integer, String>();
		matrixMap.put(0, RoutesPointsEnum.INT1.name());
		matrixMap.put(1, RoutesPointsEnum.INT2.name());
		matrixMap.put(2, RoutesPointsEnum.INT3.name());
		matrixMap.put(3, RoutesPointsEnum.INT4.name());
		matrixMap.put(4, RoutesPointsEnum.INT5.name());
		matrixMap.put(5, RoutesPointsEnum.INT6.name());
		matrixMap.put(6, RoutesPointsEnum.INT7.name());
		matrixMap.put(7, RoutesPointsEnum.INT8.name());
		matrixMap.put(8, RoutesPointsEnum.INT9.name());
		matrixMap.put(9, RoutesPointsEnum.INT10.name());
		matrixMap.put(10, RoutesPointsEnum.INT11.name());
		matrixMap.put(11, RoutesPointsEnum.INT12.name());
		matrixMap.put(12, RoutesPointsEnum.INT13.name());
		matrixMap.put(13, RoutesPointsEnum.ESC1.name());
		matrixMap.put(14, RoutesPointsEnum.ESC2.name());
		matrixMap.put(15, RoutesPointsEnum.ESC3.name());
		matrixMap.put(16, RoutesPointsEnum.DESC1.name());
		matrixMap.put(17, RoutesPointsEnum.DESC2.name());
		matrixMap.put(18, RoutesPointsEnum.DESC3.name());

		Optional<Integer> indexMatriz = matrixMap.entrySet().stream()
				.filter(entry -> entry.getValue().equalsIgnoreCase(locationCaminhao)).map(entry -> entry.getKey())
				.findFirst();

		if (indexMatriz.isEmpty()) {
			// validação caso não tenha
		}

		int start = indexMatriz.get();

		truckModel.getStatus();

		if (statusCaminhao.equalsIgnoreCase(StatusTruckEnum.CHEIO.name())) {

			int desc1 = 16;
			int desc2 = 17;
			int desc3 = 18;

			String[] distRouteDesc1 = BestRoute.calculateDistanceAndPath(m, path, start, desc1);
			String[] distRouteDesc2 = BestRoute.calculateDistanceAndPath(m, path, start, desc2);
			String[] distRouteDesc3 = BestRoute.calculateDistanceAndPath(m, path, start, desc3);

			String BR = BestRoute.calculateBestRoute(start, distRouteDesc1, distRouteDesc2, distRouteDesc3);
			return BR;

		} else if (statusCaminhao.equalsIgnoreCase(StatusTruckEnum.VAZIO.name())) {
			int esc1 = 13;
			int esc2 = 14;
			int esc3 = 15;

			String[] distRouteEsc1 = BestRoute.calculateDistanceAndPath(m, path, start, esc1);
			String[] distRouteEsc2 = BestRoute.calculateDistanceAndPath(m, path, start, esc2);
			String[] distRouteEsc3 = BestRoute.calculateDistanceAndPath(m, path, start, esc3);

			String BR = BestRoute.calculateBestRoute(start, distRouteEsc1, distRouteEsc2, distRouteEsc3);
			return BR;
		} else {
			System.out.println("Status inválido. Opçoes para Status válidos: Cheio ou Vazio");
			return null;
		}
	}

	public static int[][] shortestpath(int[][] adj, int[][] path) {
		int n = adj.length;
		int[][] ans = new int[n][n];
		copy(ans, adj);
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					if (ans[i][k] + ans[k][j] < ans[i][j]) {
						ans[i][j] = ans[i][k] + ans[k][j];
						path[i][j] = path[k][j]; // conexÃ£o entre pontos
					}
		return ans;
	}

	public static String[] calculateDistanceAndPath(int[][] m, int[][] path, int location, int destiny) {
		
		int distance = 0;
		String[] distRoute = new String[2];
		
		Map<Integer, String> matrixMap = new HashMap<Integer, String>();
		matrixMap.put(0, "INT1");
		matrixMap.put(1, "INT2");
		matrixMap.put(2, "INT3");
		matrixMap.put(3, "INT4");
		matrixMap.put(4, "INT5");
		matrixMap.put(5, "INT6");
		matrixMap.put(6, "INT7");
		matrixMap.put(7, "INT8");
		matrixMap.put(8, "INT9");
		matrixMap.put(9, "INT10");
		matrixMap.put(10, "INT11");
		matrixMap.put(11, "INT12");
		matrixMap.put(12, "INT13");
		matrixMap.put(13, "ESC1");
		matrixMap.put(14, "ESC2");
		matrixMap.put(15, "ESC3");
		matrixMap.put(16, "DESC1");
		matrixMap.put(17, "DESC2");
		matrixMap.put(18, "DESC3");

		String myPath = matrixMap.get(destiny) + "";

		while (path[location][destiny] != location) {

			distance = m[path[location][destiny]][destiny] + distance;
			myPath = matrixMap.get(path[location][destiny]) + " -> " + myPath;
			destiny = path[location][destiny];
		}
		distance = m[path[location][destiny]][destiny] + distance;

		distRoute[0] = Integer.toString(distance);
		distRoute[1] = myPath;

		return distRoute;
	}

	public static String calculateBestRoute(int location, String[] distRoute1, String[] distRoute2,
			String[] distRoute3) {
		int distR1 = Integer.parseInt(distRoute1[0]);
		int distR2 = Integer.parseInt(distRoute2[0]);
		int distR3 = Integer.parseInt(distRoute3[0]);
		String myPath = "";
		Map<Integer, String> matrixMap = new HashMap<Integer, String>();
		matrixMap.put(0, "INT1");
		matrixMap.put(1, "INT2");
		matrixMap.put(2, "INT3");
		matrixMap.put(3, "INT4");
		matrixMap.put(4, "INT5");
		matrixMap.put(5, "INT6");
		matrixMap.put(6, "INT7");
		matrixMap.put(7, "INT8");
		matrixMap.put(8, "INT9");
		matrixMap.put(9, "INT10");
		matrixMap.put(10, "INT11");
		matrixMap.put(11, "INT12");
		matrixMap.put(12, "INT13");
		matrixMap.put(13, "ESC1");
		matrixMap.put(14, "ESC2");
		matrixMap.put(15, "ESC3");
		matrixMap.put(16, "DESC1");
		matrixMap.put(17, "DESC2");
		matrixMap.put(18, "DESC3");

		if (distR1 > distR2 && distR2 > distR3) {
			myPath = distRoute3[1];

		} else if (distR1 > distR2) {
			myPath = distRoute2[1];

		} else {
			myPath = distRoute1[1];

		}

		String myBestPath = matrixMap.get(location) + " -> " + myPath;

		return myBestPath;
	}

	public static int calculateBestDistance(int location, String[] distRoute1, String[] distRoute2,
			String[] distRoute3) {
		int distR1 = Integer.parseInt(distRoute1[0]);
		int distR2 = Integer.parseInt(distRoute2[0]);
		int distR3 = Integer.parseInt(distRoute3[0]);

		Map<Integer, String> matrixMap = new HashMap<Integer, String>();
		matrixMap.put(0, "INT1");
		matrixMap.put(1, "INT2");
		matrixMap.put(2, "INT3");
		matrixMap.put(3, "INT4");
		matrixMap.put(4, "INT5");
		matrixMap.put(5, "INT6");
		matrixMap.put(6, "INT7");
		matrixMap.put(7, "INT8");
		matrixMap.put(8, "INT9");
		matrixMap.put(9, "INT10");
		matrixMap.put(10, "INT11");
		matrixMap.put(11, "INT12");
		matrixMap.put(12, "INT13");
		matrixMap.put(13, "ESC1");
		matrixMap.put(14, "ESC2");
		matrixMap.put(15, "ESC3");
		matrixMap.put(16, "DESC1");
		matrixMap.put(17, "DESC2");
		matrixMap.put(18, "DESC3");

		if (distR1 > distR2 && distR2 > distR3) {
			return distR3;

		} else if (distR1 > distR2) {
			return distR2;

		} else {
			return distR1;
		}
	}

	public static void copy(int[][] a, int[][] b) {
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[0].length; j++)
				a[i][j] = b[i][j];
	}
}