
posv(-3..2).
boxv(0..2).
rotv(0..2).

% Every piece has exactly one rotation in each axes


1{rotx(P,R) : rotv(R)}1 :-piece(P) .
1{roty(P,R) : rotv(R)}1 :-piece(P) .
1{rotz(P,R) : rotv(R)}1 :-piece(P) .



partr0(P,0,0,0,X,Y,Z):-piece(P),part(P,X,Y,Z).

partr3(P,RX,RY,RZ+1,0-Y,X,Z):-
	partr2(P,RX,RY,RZ,X,Y,Z),
	rotv(RX;RY;RZ;RZ+1),
	posv(X;Y;Z;NX),	piece(P).

partr3(P,RX,RY,RZ+1,0-Y,X,Z):-
	partr3(P,RX,RY,RZ,X,Y,Z),
	rotv(RX;RY;RZ;RZ+1),
	posv(X;Y;Z;NX),	piece(P).

partr3(P,RX,RY,RZ+1,0-Y,X,Z):-
	partr1(P,RX,RY,RZ,X,Y,Z),
	rotv(RX;RY;RZ;RZ+1),
	posv(X;Y;Z;NX),	piece(P).


partr3(P,RX,RY,RZ+1,0-Y,X,Z):-
	partr0(P,RX,RY,RZ,X,Y,Z),
	rotv(RX;RY;RZ;RZ+1),
	posv(X;Y;Z;NX),	piece(P).


partr2(P,RX,RY+1,RZ,0-Z,Y,X):-
	piece(P),
	partr1(P,RX,RY,RZ,X,Y,Z),
	rotv(RX;RY;RZ;RY+1),
	posv(X;Y;Z).

partr2(P,RX,RY+1,RZ,0-Z,Y,X):-
	piece(P),
	partr2(P,RX,RY,RZ,X,Y,Z),
	rotv(RX;RY;RZ;RY+1),
	posv(X;Y;Z).

partr2(P,RX,RY+1,RZ,0-Z,Y,X):-
	piece(P),
	partr0(P,RX,RY,RZ,X,Y,Z),
	rotv(RX;RY;RZ;RY+1),
	posv(X;Y;Z).

partr1(P,RX+1,RY,RZ,X,Z,0-Y):-
	piece(P),
	partr0(P,RX,RY,RZ,X,Y,Z),
	rotv(RX;RY;RZ;RX+1),
	posv(X;Y;Z).

partr1(P,RX+1,RY,RZ,X,Z,0-Y):-
	piece(P),
	partr1(P,RX,RY,RZ,X,Y,Z),
	rotv(RX;RY;RZ;RX+1),
	posv(X;Y;Z).
	

trnv(0).


1{tx(P,TX):trnv(TX)}1 :- piece(P).
1{ty(P,TX):trnv(TX)}1 :- piece(P).
1{tz(P,TX):trnv(TX)}1 :- piece(P).

% final position of each part of each piece
partf(P,NX+TX,NY+TY,NZ+TZ):-
      piece(P),
      tx(P,TX),
      ty(P,TY),
      tz(P,TZ),
      rotx(P,RX),
      roty(P,RY),
      rotz(P,RZ),
      rotv(RX;RY;RZ),
      partr3(P,RX,RY,RZ,NX,NY,NZ),
       boxv(NX+TX;NY+TY;NZ+TZ).


partf(P,NX,NY,NZ):-
      piece(P),
      rotx(P,RX),
      roty(P,RY),
      rotz(P,RZ),
      rotv(RX;RY;RZ),
      partr2(P,RX,RY,RZ,NX,NY,NZ),boxv(NX;NY;NZ).

partf(P,NX,NY,NZ):-
      piece(P),
      rotx(P,RX),
      roty(P,RY),
      rotz(P,RZ),
      rotv(RX;RY;RZ),
      partr1(P,RX,RY,RZ,NX,NY,NZ),boxv(NX;NY;NZ).

partf(P,NX,NY,NZ):-
      piece(P),
      rotx(P,RX),
      roty(P,RY),
      rotz(P,RZ),
      rotv(RX;RY;RZ),
      partr0(P,RX,RY,RZ,NX,NY,NZ),boxv(NX;NY;NZ).




:- partf(P1,X,Y,Z), partf(P2,X,Y,Z), P1!=P2,piece(P1;P2),posv(X),posv(Y),posv(Z).

filled(X,Y,Z):- partf(P,X,Y,Z),piece(P),posv(X),posv(Y),posv(Z).


#show rotx/2.
#show roty/2.
#show rotz/2.
#show partf/4.
#show tx/2.
#show ty/2.
#show tz/2.
#show piece/1.
#show part/4.
#show partf/4.


%#show partr(P,F,R,T,X,Y,Z).
%#show piecer(P,X,Y,Z).
%#hide posxp(X).
%#hide posyp(X).
%#hide poszp(X).
%#hide trnv(X).
