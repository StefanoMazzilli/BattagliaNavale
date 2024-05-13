package org.generation.italy;

import java.util.Random;
import java.util.Scanner;

class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		Random r=new Random();
		
		int[][] grigliaCpu=new int[8][8];
		int[][] grigliaG=new int[8][8];
		int colonna, riga, i, j, k=1;
		boolean vittoriaCpu=false, vittoriaG=false;
		
		//riempimento delle due griglie
		for (i=0; i<8; i++) {
			for (j=0; j<8; j++) {
				grigliaCpu[i][j]=0;
				grigliaG[i][j]=0;
			}
		}
		
		//chiedo al giocatore dove vuole inserire la nave
		System.out.print("Inserire la colonna in cui posizionare la nave: ");
		colonna= sc.nextInt();
		System.out.print("Inserire la riga in cui posizionare la nave: ");
		riga= sc.nextInt();
		grigliaG[riga-1][colonna-1]=1;
		
		//ora inserisco la nave del computer casualmente
		colonna=r.nextInt(8);
		riga=r.nextInt(8);
		grigliaCpu[riga][colonna]=1;
		
		//adesso mostro il campo da gioco iniziale
		System.out.println("Campo del computer");
		for (i=0; i<8; i++) {
			for (j=0; j<8; j++) {
				System.out.print("[ ]");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Campo del giocatore");
		for (i=0; i<8; i++) {
			for (j=0; j<8; j++) {
				if (grigliaG[i][j]==0) 
					System.out.print("[ ]");
				else 
					System.out.print("[V]");
			}
			System.out.println();
		}
		
		//inizio dei turni di gioco
		do {
			if (k%2==1) {
				//turno del giocatore quando k è dispari
				System.out.println("Dove vuoi colpire?");
				System.out.print("Colonna: ");
				colonna= sc.nextInt();
				System.out.print("Riga: ");
				riga= sc.nextInt();
				
				//controllo la mossa del giocatore
				if (grigliaCpu[riga-1][colonna-1]==0) {
					System.out.println("Mancato!");
					grigliaCpu[riga-1][colonna-1]=3;
				}else if (grigliaCpu[riga-1][colonna-1]==1) {
					System.out.println("Colpito!");
					grigliaCpu[riga-1][colonna-1]=2;
					vittoriaG=true;
				}else {
					System.out.println("Hai già colpito questo punto!");
				}
			}else {
				//turno del computer quando k è pari
				//genero dei numeri casuali da colpire
				colonna=r.nextInt(8);
				riga=r.nextInt(8);
				//controllo la mossa del giocatore
				if (grigliaG[riga][colonna]==1) {
					System.out.println("Sei stato colpito!");
					grigliaG[riga][colonna]=2;
					vittoriaCpu=true;
				}else {
					System.out.println("Sei stato mancato!");
					grigliaG[riga][colonna]=3;
				}
			}
			
			//faccio avanzare il conteggio dei turni
			k++;
			
			//mostro i due campi
			System.out.println("Campo del computer");
			for(i=0; i<8; i++) {
				for(j=0;j<8;j++) {
					if (grigliaCpu[i][j]==0||grigliaCpu[i][j]==1) {
						System.out.print("[ ]");
					}else if (grigliaCpu[i][j]==2) {
						System.out.print("[X]");
					}else {
						System.out.print("[O]");
					}
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("Campo del giocatore");
			for(i=0;i<8;i++) {
				for(j=0;j<8;j++) {
					if(grigliaG[i][j]==0) {
						System.out.print("[ ]");
					}else if (grigliaG[i][j]==1) {
						System.out.print("[V]");
					}else if (grigliaG[i][j]==2) {
						System.out.print("[X]");
					}else {
						System.out.print("[O]");
					}
				}
				System.out.println();
			}
			for (i=0;i<2000000000;i++) {
				//ciclo di ritardo tra un turno e l'altro
			}
		}while(!(vittoriaCpu)&&!(vittoriaG));
		
		//assegno il vincitore
		if(vittoriaG==true) {
			System.out.println("Complimenti! Hai vinto!!");
		}else {
			System.out.println("Peccato, ha vinto il computer.");
		}
		
		
		sc.close();
	}

}
