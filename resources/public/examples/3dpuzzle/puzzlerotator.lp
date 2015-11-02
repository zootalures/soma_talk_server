

% Every piece has exactly one rotation and one transalation


1{rotx(P,R) : rotv(R)}1 :-piece(P), .
1{roty(P,R) : rotv(R)}1 :-piece(P) .
1{rotz(P,R) : rotv(R)}1 :-piece(P) .


1{tx(P,TX):trnv(TX)}1 :- piece(P).
1{ty(P,TX):trnv(TX)}1 :- piece(P).
1{tz(P,TX):trnv(TX)}1 :- piece(P).


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
