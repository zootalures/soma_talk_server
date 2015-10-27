#!/bin/bash

rm -rf outfile/*

mkdir -p  outfile/emptygrid
../../aspviz -v visualise.aspviz -o outfile/emptygrid puzzle-hard.lp

mkdir -p outfile/filledgrid
../../aspviz -v visualise.aspviz -o outfile/filledgrid puzzle-hard.lp su-doku.lp

mkdir -p outfile/anim
../../aspviz -v visualise-anim.aspviz -o outfile/anim puzzle-hard.lp su-doku.lp


for i in outfile/*/*.svg; do
j=`echo $i | sed -e 's/svg/pdf/'`; 
echo $j; 
rm -f $j;
~occ/scripts/svg2pdf $i $j
done