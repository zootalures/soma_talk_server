piece(N):- part(N,_).
axis(x).
axis(y).
axis(z).
rotv(0..2).

1{rot(P, A, R) : rotv(R) }1 :- piece(P),axis(A).

%rotated part location

#show rot/3.

