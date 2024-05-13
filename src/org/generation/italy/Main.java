package org.generation.italy;

import java.util.Random;
import java.util.Scanner;

class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Random r = new Random();
		
		final int numNavi=1;
		int[][] grigliaCpu = new int[8][8];
		int[][] grigliaG = new int[8][8];
		int colonna1, colonna2, colonna, riga, riga1, riga2, i, j, k = 1;
		//boolean vittoriaCpu = false, vittoriaG = false;
		int[][] naveCpu = new int[numNavi][2]; //matrice navi giocatore: num nave - lunghezza nave rimanente
		int[][] naveG = new int[numNavi][2]; //stessa cosa della matrice nave computer
		String verso = new String();
		int versoCpu, affondateG = 0, affondateCpu = 0;

		// riempimento delle due griglie
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				grigliaCpu[i][j] = 0;
				grigliaG[i][j] = 0;
			}
		}
		
		//Inserisco i numeri delle navi nelle matrici navi
		naveG[0][0] = 1;
		naveCpu[0][0] = 1;
		naveG[0][1] = 0;
		naveCpu[0][1] = 0;
		
		// chiedo al giocatore dove vuole inserire la nave e con quale verso
		System.out.println("In che verso vuoi posizionare la nave?");
		verso = sc.nextLine().toLowerCase();

		// controllo l'inserimento del verso
		while (!(verso.equals("orizzontale")) && !(verso.equals("verticale"))) {
			System.out.println("Inserimento non valido, riprovare con: orizzontale o verticale");
			verso = sc.nextLine().toLowerCase();
		}
		if (verso.equals("orizzontale")) {
			System.out.print("Inserire la colonna di partenza in cui posizionare la nave: ");
			colonna1 = sc.nextInt();
			while (colonna1>7) {
				System.out.println("Inserimento non valido, sei troppo a destra! Rirpova: ");
				colonna1 = sc.nextInt();
			}
			colonna2=colonna1+1;
			System.out.print("Inserire la riga in cui posizionare la nave: ");
			riga1 = sc.nextInt();
			sc.nextLine();
			riga2=riga1;
		} else {
			System.out.print("Inserire la colonna in cui posizionare la nave: ");
			colonna1 = sc.nextInt();
			colonna2=colonna1;
			System.out.print("Inserire la riga di partenza in cui posizionare la nave: ");
			riga1 = sc.nextInt();
			sc.nextLine();
			while(riga1>7) {
				System.out.println("Inserimento non valido, sei troppo in basso! Riprova: ");
				riga1 = sc.nextInt();
			}
			riga2=riga1+1;
		}
		for (i=colonna1-1;i<colonna2; i++) {
			for (j=riga1-1; j<riga2; j++) {
				grigliaG[j][i]=naveG[0][0];
				naveG[0][1]++; //aggiungo la lunghezza attuale della nave nella matrice navi giocatore
			}
		}
		
		// ora inserisco la nave del computer casualmente
		versoCpu = r.nextInt(2); //determino il verso casualmente: 0 -> orizzontale; 1 -> verticale
		if (versoCpu==0) {
			colonna1=r.nextInt(7);
			colonna2=colonna1+1;
			riga1=r.nextInt(8);
			riga2=riga1;
		} else {
			colonna1=r.nextInt(8);
			colonna2=colonna1;
			riga1=r.nextInt(7);
			riga2=riga1+1;
		}
		for (i=colonna1; i<=colonna2; i++) {
			for (j=riga1; j<=riga2; j++) {
				grigliaCpu[j][i]=naveCpu[0][0];
				naveCpu[0][1]++; //aggiungo la lunghezza attuale della nave nella matrice navi computer
			}
		}
		
		// adesso mostro il campo da gioco iniziale
		System.out.println("Campo del computer");
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				System.out.print("[ ]");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Campo del giocatore");
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if (grigliaG[i][j] == 0)
					System.out.print("[ ]");
				else
					System.out.print("[V]");
			}
			System.out.println();
		}

		// inizio dei turni di gioco
		do {
			if (k % 2 == 1) {
				// turno del giocatore quando k è dispari
				System.out.println("Dove vuoi colpire?");
				System.out.print("Colonna: ");
				colonna = sc.nextInt();
				System.out.print("Riga: ");
				riga = sc.nextInt();

				// controllo la mossa del giocatore
				if (grigliaCpu[riga - 1][colonna - 1] == 0) {
					System.out.println("Mancato!");
					grigliaCpu[riga - 1][colonna - 1] = 7;
				} else if ((grigliaCpu[riga - 1][colonna - 1] > 0)&&(grigliaCpu[riga - 1][colonna - 1] < 6)) {
					for (i=0; i<numNavi; i++) {
						if (grigliaCpu[riga - 1][colonna - 1]==naveCpu[i][0]) {
							naveCpu[i][1]--;
							if (naveCpu[i][1]==0) {
								System.out.println("Colpito e affondato!!");
								affondateCpu++;
							}else
								System.out.println("Colpito!");
						}
					}
					grigliaCpu[riga - 1][colonna - 1] = 6;
				} else {
					System.out.println("Hai già colpito questo punto!");
				}
			} else {
				// turno del computer quando k è pari
				// genero dei numeri casuali da colpire
				colonna = r.nextInt(8);
				riga = r.nextInt(8);
				// controllo la mossa del giocatore
				if ((grigliaG[riga][colonna] > 0)&&(grigliaG[riga][colonna] < 6)) {
					for (i=0; i<numNavi; i++) {
						if (grigliaG[riga][colonna]==naveG[i][0]) {
							naveG[i][1]--;
							if (naveG[i][1]==0) {
								System.out.println("Sei stato colpito e affondato!!");
								affondateG++;
							} else {
								System.out.println("Sei stato colpito!");
							}
						}
					}
					grigliaG[riga][colonna] = 6;
				} else {
					System.out.println("Sei stato mancato!");
					grigliaG[riga][colonna] = 7;
				}
			}

			// faccio avanzare il conteggio dei turni
			k++;

			// mostro i due campi
			System.out.println("Campo del computer");
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					if (grigliaCpu[i][j] < 6) {
						System.out.print("[ ]");
					} else if (grigliaCpu[i][j] == 6) {
						System.out.print("[X]");
					} else {
						System.out.print("[O]");
					}
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("Campo del giocatore");
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					if (grigliaG[i][j] == 0) {
						System.out.print("[ ]");
					} else if ((grigliaG[i][j] >= 1)&&(grigliaG[i][j]<6)) {
						System.out.print("[V]");
					} else if (grigliaG[i][j] == 6) {
						System.out.print("[X]");
					} else {
						System.out.print("[O]");
					}
				}
				System.out.println();
			}
			for (i = 0; i < 2000000000; i++) {
				// ciclo di ritardo tra un turno e l'altro
			}
		} while ((affondateCpu<numNavi) && (affondateG < numNavi));

		// assegno il vincitore
		if (affondateCpu==numNavi) {
			System.out.println("Complimenti! Hai vinto!!");
		} else {
			System.out.println("Peccato, ha vinto il computer.");
		}
		
		sc.close();
	}

}
