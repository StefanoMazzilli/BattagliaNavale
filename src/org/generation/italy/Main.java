package org.generation.italy;

import java.util.Random;
import java.util.Scanner;

class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Random r = new Random();
		
		final int numNavi=5;
		int[][] grigliaCpu = new int[9][9];
		int[][] grigliaG = new int[9][9];
		int colonna1, colonna2, colonna, riga, riga1, riga2, i, j, n, k = 1;
		boolean occupato, mancato;
		int[][] naveCpu = new int[numNavi][3]; //matrice navi giocatore: num nave - lunghezza nave rimanente
		int[][] naveG = new int[numNavi][3]; //stessa cosa della matrice nave computer
		String verso = new String();
		int versoCpu, affondateG = 0, affondateCpu = 0;
		
		/*
		 * DA AGGIUNGERE:
		 *  - visualizzazione di navi rimanenti alleate ed avversarie;
		 *  - possibilità di scelta numero navi e lunghezza;
		 *  - possibilità per il computer di colpire intorno alle navi colpite;
		 *  - migliorie varie alla strategia del computer (colpi a scacchiera, probabilità presenza navi?)
		 */

		// riempimento delle due griglie
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				grigliaCpu[i][j] = 0;
				grigliaG[i][j] = 0;
			}
		}
		
		//Inserisco i numeri delle navi, caselle presenti e lunghezza totale delle navi nelle matrici navi
		for(i=0; i<numNavi; i++) {
			/*
			 * ogni nave avrà: un numero da 1 a 5 che le distingue nella casella (i,0);
			 * la lunghezza temporanea con cui conto se la nave è affondata o meno nella casella (i,1);
			 * la lunghezza totale nella casella (i,2) che sarà 2 per le prime due navi, 3 per le successive due e 4 per l'ultima
			 */
			naveG[i][0]=i+1;
			naveCpu[i][0]=i+1;
			naveG[i][1]=0;
			naveCpu[i][1]=0;
			naveG[i][2]=(i/2)+2;
			naveCpu[i][2]=(i/2)+2;
		}
		
		// chiedo al giocatore dove vuole inserire le navi e con quale verso
		for (n=0; n<numNavi; n++) {
			do {
				occupato=false;
				System.out.println("\nInserimento nave "+(n+1)+" di lunghezza "+naveG[n][2]);
				System.out.print("\nCon quale verso vuoi posizionare la nave "+(n+1)+"? ");
				verso = sc.nextLine().toLowerCase();
		
				// controllo l'inserimento del verso
				while (!(verso.equals("orizzontale")) && !(verso.equals("verticale"))) {
					System.out.print("Inserimento non valido, riprovare con: orizzontale o verticale. ");
					verso = sc.nextLine().toLowerCase();
				}
				
				if (verso.equals("orizzontale")) {
					System.out.print("Inserire la colonna di partenza in cui posizionare la nave "+(n+1)+": ");
					colonna1 = sc.nextInt();
					//controllo l'inserimento valido di colonne e righe
					while (colonna1>(9-naveG[n][2])&&(colonna1<9)) {
						System.out.print("Inserimento non valido, sei troppo a destra!\nRirpova: ");
						colonna1 = sc.nextInt();
					}
					while ((colonna1<=0)||(colonna1>=9)) {
						System.out.print("Inserimento non valido, colonna inesistente!\nRiprova: ");
						colonna1 = sc.nextInt();
					}
					colonna2=colonna1+(naveG[n][2]-1);
					System.out.print("Inserire la riga in cui posizionare la nave "+(n+1)+": ");
					riga1 = sc.nextInt();
					sc.nextLine();
					while ((riga1<=0)||(riga1>=9)) {
						System.out.print("Inserimento non valido, riga inesistente!\nRiprova: ");
						riga1 = sc.nextInt();
						sc.nextLine();
					}
					riga2=riga1;
				} else {
					System.out.print("Inserire la colonna in cui posizionare la nave "+(n+1)+": ");
					colonna1 = sc.nextInt();
					//controllo l'inserimento valido di colonne e righe
					while ((colonna1<=0)||(colonna1>=9)) {
						System.out.print("Inserimento non valido, colonna inesistente!\nRiprova: ");
						colonna1 = sc.nextInt();
					}
					colonna2=colonna1;
					System.out.print("Inserire la riga di partenza in cui posizionare la nave "+(n+1)+": ");
					riga1 = sc.nextInt();
					sc.nextLine();
					while(riga1>(9-naveG[n][2])&&(riga1<9)) {
						System.out.print("Inserimento non valido, sei troppo in basso! Riprova: ");
						riga1 = sc.nextInt();
						sc.nextLine();
					}
					while ((riga1<=0)||(riga1>=9)) {
						System.out.print("Inserimento non valido, riga inesistente!\nRiprova: ");
						riga1 = sc.nextInt();
						sc.nextLine();
					}
					riga2=riga1+(naveG[n][2]-1);
				}
				//controllo se la parte selezionata è libera
				for (i=colonna1;i<=colonna2;i++) {
					for (j=riga1;j<=riga2;j++) {
						if (grigliaG[j][i]!=0) {
							occupato=true;
						}
					}
				}
				if (!occupato) {
					//se la linea selezionata è libera allora posiziono la nave
					for (i=colonna1;i<=colonna2; i++) {
						for (j=riga1; j<=riga2; j++) {
							grigliaG[j][i]=naveG[n][0];
							naveG[n][1]++; //aggiungo la lunghezza attuale della nave nella matrice navi giocatore
						}
					}
				} else {
					System.out.println("\nATTENZIONE!! Le caselle selezionate non sono libere!");
				}
			}while(naveG[n][1]!=naveG[n][2]); //inizialmente i due valori devono combaciare
			
			//ogni volta gli faccio vedere dove sono state inserite le navi del giocatore
			System.out.println("\n\nCampo del giocatore");
			for (i = 0; i < 9; i++) {
				for (j = 0; j < 9; j++) {
					if ((i==0)||(j==0)) {
						System.out.print("["+(i+j)+"]");
					} else {
						if (grigliaG[i][j] == 0)
							System.out.print("[ ]");
						else
							System.out.print("["+"\u001B[33m"+"V"+"\u001B[37m"+"]");
					}
				}
				System.out.println();
			}
			
		}
		
		// ora inserisco la nave del computer casualmente
		for (n=0; n<numNavi; n++) {
			do {
				occupato=false;
				versoCpu = r.nextInt(2); //determino il verso casualmente: 0 -> orizzontale; 1 -> verticale
				if (versoCpu==0) {
					colonna1=r.nextInt((9-naveCpu[n][2]))+1;
					colonna2=colonna1+(naveCpu[n][2]-1);
					riga1=r.nextInt(8)+1;
					riga2=riga1;
				} else {
					colonna1=r.nextInt(8)+1;
					colonna2=colonna1;
					riga1=r.nextInt((9-naveCpu[n][2]))+1;
					riga2=riga1+(naveCpu[n][2]-1);
				}
				
				//controllo se le righe generate dal computer sono libere
				for (i=colonna1; i<=colonna2; i++) {
					for (j=riga1; j<=riga2; j++) {
						if (grigliaCpu[j][i]!=0) {
							occupato=true;
						}
					}
				}
				//se è libera allora inserisco la nave
				if (!occupato) {
					for (i=colonna1; i<=colonna2; i++) {
						for (j=riga1; j<=riga2; j++) {
							grigliaCpu[j][i]=naveCpu[n][0];
							naveCpu[n][1]++; //aggiungo la lunghezza attuale della nave nella matrice navi computer
						}
					}
				}
			}while(naveCpu[n][1]!=naveCpu[n][2]);
		}

		// inizio dei turni di gioco
		do {
			mancato = false;
			if (k % 2 == 1) {
				
				// turno del giocatore quando k è dispari
				// mostro i due campi ogni volta che inizia il turno del giocatore
				System.out.println("\n\n\nCampo del computer");
				for (i = 0; i < 9; i++) {
					for (j = 0; j < 9; j++) {
						if ((i==0)||(j==0)) {
							System.out.print("["+(i+j)+"]");
						}else {
							if (grigliaCpu[i][j] < 6) {
								System.out.print("[ ]");
							} else if (grigliaCpu[i][j] == 6) {
								System.out.print("["+"\u001B[31m"+"X"+"\u001B[37m"+"]");
							} else {
								System.out.print("["+"\u001B[36m"+"O"+"\u001B[37m"+"]");
							}
						}
					}
					System.out.println();
				}
				System.out.println();
				System.out.println("\nCampo del giocatore");
				for (i = 0; i < 9; i++) {
					for (j = 0; j < 9; j++) {
						if ((i==0)||(j==0)) {
							System.out.print("["+(i+j)+"]");
						}else {
							if (grigliaG[i][j] == 0) {
								System.out.print("[ ]");
							} else if ((grigliaG[i][j] >= 1)&&(grigliaG[i][j]<6)) {
								System.out.print("["+"\u001B[33m"+"V"+"\u001B[37m"+"]");
							} else if (grigliaG[i][j] == 6) {
								System.out.print("["+"\u001B[31m"+"X"+"\u001B[37m"+"]");
							} else {
								System.out.print("["+"\u001B[36m"+"O"+"\u001B[37m"+"]");
							}
						}
					}
					System.out.println();
				}
				
				System.out.println("\nDove vuoi colpire?");
				System.out.print("Colonna: ");
				colonna = sc.nextInt();
				while ((colonna<=0)||(colonna>=9)) {
					System.out.print("ATTENZIONE!! Colonna inesistente!\nRiprova: ");
					colonna = sc.nextInt();
				}
				System.out.print("Riga: ");
				riga = sc.nextInt();
				while ((riga<=0)||(riga>=9)) {
					System.out.print("ATTENZIONE!! Riga inesistente!\nRiprova: ");
					riga = sc.nextInt();
				}
				// controllo la mossa del giocatore
				while ((grigliaCpu[riga][colonna]==6)||(grigliaCpu[riga][colonna]==7)) {
					System.out.println("ATTENZIONE!! Casella già colpita! Riprova!");
					System.out.print("Colonna: ");
					colonna = sc.nextInt();
					System.out.print("Riga: ");
					riga = sc.nextInt();
				}
				if (grigliaCpu[riga][colonna] == 0) {
					System.out.println("\nMancato!");
					grigliaCpu[riga][colonna] = 7;
					mancato = true;
				} else if ((grigliaCpu[riga][colonna] > 0)&&(grigliaCpu[riga][colonna] < 6)) {
					for (i=0; i<numNavi; i++) {
						if (grigliaCpu[riga][colonna]==naveCpu[i][0]) {
							naveCpu[i][1]--;
							if (naveCpu[i][1]==0) {
								System.out.println("\nColpito e affondato!!");
								affondateCpu++;
							}else
								System.out.println("\nColpito!");
						}
					}
					grigliaCpu[riga][colonna] = 6;
				}
				
			} else {
				
				// turno del computer quando k è pari
				// genero dei numeri casuali da colpire che non abbiano già colpito il bersaglio
				do {
					colonna = r.nextInt(8)+1;
					riga = r.nextInt(8)+1;
				} while ((grigliaG[riga][colonna]==6)||(grigliaG[riga][colonna]==7));
				//mostro al giocatore la mossa del computer
				System.out.println("\n\nColonna: "+colonna+"\nRiga: "+riga);
				// controllo la mossa del computer
				if ((grigliaG[riga][colonna] > 0)&&(grigliaG[riga][colonna] < 6)) {
					for (i=0; i<numNavi; i++) {
						if (grigliaG[riga][colonna]==naveG[i][0]) {
							naveG[i][1]--;
							if (naveG[i][1]==0) {
								System.out.println("\nSei stato colpito e affondato!!");
								affondateG++;
							} else {
								System.out.println("\nSei stato colpito!");
								}
						}
					}
					grigliaG[riga][colonna] = 6;
				} else {
					System.out.println("\nSei stato mancato!");
					grigliaG[riga][colonna] = 7;
					mancato = true;
				}
			}

			// faccio avanzare il conteggio dei turni solo se l'attuale giocatore ha mancato il bersaglio
			if (mancato) 
				k++;

			for (i = -2000000000; i < 2000000000; i++) {
				// ciclo di ritardo tra un turno e l'altro
			}
		} while ((affondateCpu<numNavi) && (affondateG < numNavi));

		// assegno il vincitore
		if (affondateCpu==numNavi) {
			System.out.println("\n\nCOMPLIMENTI! Hai vinto!!");
		} else {
			System.out.println("\n\nPeccato, ha vinto il computer.");
		}
		
		sc.close();
	}

}
