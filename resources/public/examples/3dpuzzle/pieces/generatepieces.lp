#show part/1.

% 3x3 cube
pos(0..2).
posz(0..2).
%axes
axis(x;y;z).

% generate all combinations of 3 and four blocks in the cube
3 { part(p(X,Y,Z)) : pos(X), pos(Y), posz(Z)} 4.

%stage 1

% index the location of each part in each axis
loc(x,p(X,Y,Z),X) :- part(p(X,Y,Z)).
loc(y,p(X,Y,Z),Y) :- part(p(X,Y,Z)).
loc(z,p(X,Y,Z),Z) :- part(p(X,Y,Z)).

% translational invariance - only include the parts touch the X,Y, and  Z planes simultaneously
touching(A) :- part(P), loc(A,P,0).

:- not touching(x).
:- not touching(y).
:- not touching(z).

%stage 2


% make sure they're connected -
adjacent(A,P1,P2) :-
    loc(A,P1,R), loc(A, P2, R+1), % adjacent axes
    loc(B,P1,S), loc(B, P2, S),  % same axes
    loc(C,P1,T), loc(C, P2, T),  % same axes
    B!=C,A!=B,A!=C,B!=C,
         part(P1),part(P2).

% adjacency is commutative
adjacent(A, P1,P2) :- adjacent(A, P2,P1).
%stage 3

% connected is adjacent
connected(P1,P2) :- adjacent (_,P1, P2).

% connectedness is transitive
connected(P1,P3) :- connected(P1,P2), connected(P2,P3) .

% ignore models which have disconnections
disconnected :- not connected(P1, P2), part(P1), part(P2).
:- disconnected.

%stage 4

% a corner  consists of three adjacent pieces, adjacent in two distinct axes.
% but excluding the cas where there is a piece inside the corner
corner(P2,A3) :-
 adjacent(A1,P1,P2), adjacent(A2,P2,P3),
 axis(A3), A1!=A2, A2!=A3,
 P1!=P3 , A1!=A2,A1!=A3.

hasinflection :- corner(P2,A3), not oppositecorners(P1,_,A3),part(P1).

% only interested in pieces with at least one corner
:- not hasinflection.


%stage 5
oppositecorners(P1,P2,A):- corner(P1,A), corner(P2,A),
 adjacent(_,P1,P3), adjacent(_,P2,P3), P1!=P3,P1!=P2.



