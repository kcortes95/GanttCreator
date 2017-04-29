#include <stdio.h>
#include "macros.c"

struct Thread{
	int type; //0 ULT ó 1 KLT //By default, 0
	int cpu_io[MAX_CPU_IO];
};
