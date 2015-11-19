axis(x;y;z).

angle(0;90;180;270).

% possible rotations
rotv(A, V):- axis(A), angle(V). 

sin(0,0). sin(90,1). sin(180,0). sin(270,-1).

cos(0,1). cos(90,0). cos(180,-1). cos(270,0).

% Every piece has exactly one rotation in each axis
1{ rot(P,A,R) :  rotv(A,R) } 1 :- piece(P),axis(A).

% Place an element into space based on its rotation and translation
place_rotated_part(P,r(RX,RY,RZ),p(X,Y,Z)) :-
   part(P,p(IX,IY,IZ)),
   rot(P,x,RX),rot(P,y,RY),  rot(P,z,RZ),
   sin(RX,SRX), sin(RY,SRY), sin(RZ,SRZ),
   cos(RX,CRX), cos(RY,CRY), cos(RZ,CRZ),
   X = (((((IY * SRX) + (IZ - CRX)) * SRY) + (IX * CRY)) * CRZ) - (((IY * CRX) - (IZ - SRX))  * SRZ),
   Y = (((((IY * SRX) + (IZ - CRX)) * SRY) + (IX * CRY)) * SRZ) + (((IY * CRX) - (IZ - SRX)) * CRZ),
   Z = (((IY * SRX) + (IZ - CRX)) * CRY) - (IX * SRY) .

np(X,Y,Z):- place_rotated_part(_,_,p(X,Y,Z)).

piece(P):- part(P,_).

part(0,p(1,1,)).


#show np/3.
%#show place_rotated_part/3.
%#show rot/3.
%#show tx/3.
