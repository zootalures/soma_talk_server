%placepart(0,r(2,2,3),t(1,0,1),p(2,1,1)).

piece(P) :- placepart(P,_,_,_).
rot(R) :- placepart(_,R,_,_).
tran(T) :- placepart(_,_,T,_).
loc(L) :- placepart(_,_,_,L).

% pick exactly one rotation and translation for each piece
1{translate_part(P,R,T) : placepart(P,R,T,_) }1 :- piece(P) .

occupied_by(P,L):- translate_part(P,R,T),placepart(P,R,T,L).

:- occupied_by(P1,L),occupied_by(P2,L),P1!=P2.

occupied(L):- occupied_by(P,L).
:- not occupied(L), loc(L).

#show piece/1.
%#show translate_part/3.
%#show occupied_by/2.
