#include <stdio.h>

struct Thread{
	int type; //0 ULT ó 1 KLT //By default, 0
	int arrival_time;
	int cpu_io[12];
};

struct Process{
	struct Thread threads[3];
	int id;
	int total_threads;
	int status; //ejecutando, bloqueado
	int remaining_time; //para hacer el SPN
};

struct Process processes[10];

struct Process updateProcesses(int p, int t, int type_t, FILE* file);
struct Thread fill_cpu_io_time(FILE* file, struct Thread t);

int main(void){
	int c;
	FILE *file;
	file = fopen("sample.txt", "r");
	
	int line = 0;

	int generalAlgorithm = 0;
	int ultAlgorithm = 0; 	
	int cores = 0;
	int total_processes = 0;

	if (file) {
		while ((c = getc(file)) != EOF){

			if(c=='\n')
				line++;

			//se queda siempre con el ultimo numero que encuentra en el archivo
			if(line==0)
				generalAlgorithm = c - '0';

			if(line==1)
				ultAlgorithm = c - '0';			

			if(line==2)
				cores = c - '0';

			if(line==3)
				total_processes = c - '0'; //max: 10

			if(line != 0 && line != 1 && line != 2 && line != 3){
				if(c=='P'){
					int p = (int)getc(file)-'0'; //nro del proceso
					int t = (int)getc(file)-'0'; //nro del hilo
					int type_t = (int)getc(file)-'0'; //si es ult o klt
					processes[p] = updateProcesses(p,t, type_t, file);
				}
			}

		}
		fclose(file);
			
			for(int i = 0 ; i < total_processes ; i++){
				printf("id: %d\n",processes[i].id);
			}

	}

	printf("\ngeneralAlgorithm: %d \n", generalAlgorithm);
	printf("ultAlgorithm: %d \n", ultAlgorithm);
	printf("cores: %d\n",cores);
	printf("Total processes: %d\n",total_processes);

}

/*
** Crea el proceso si no estaba y agrega el hilo
** Si estaba, agrega el hilo correspondiente
*/
struct Process updateProcesses(int p, int t, int type_t, FILE* file){

	struct Process p_aux = processes[p];
	p_aux.id = p;

	char* type = "";
	if(type_t == 0)
		type = "ULT";
	else
		type = "KLT";

	struct Thread thread;
	thread.type = type_t;
	thread = fill_cpu_io_time(file, thread);
	p_aux.threads[t] = thread;

	printf("Tomo el proceso %d, hilo %d %s \n", p, t, type);

	return p_aux;
}

struct Thread fill_cpu_io_time(FILE* file, struct Thread t){
		int c;
		c = getc(file);
		struct Thread t_aux = t;
		t_aux.arrival_time = (int) (getc(file) - '0');
		printf("Tiempo de arrivo: %d\n",t_aux.arrival_time);		
		
		/*
		int counter = 0;
		while( (c = getc(file)) != '\n' ){
			t_aux.cpu_io[counter] = c-'0';
			printf("valor: %d\n", (int)c-'0');
			counter++;
		}
		*/

		return t_aux;
}

