

#const gridx=2.
#const gridy=2.
#const gridz=2.

posx(0..gridx).
posy(0..gridy).
posz(0..gridz).

piece(a).
part(a,p(0,0,0)).
part(a,p(0,1,0)).
part(a,p(1,0,0)).
part(a,p(2,0,0)).


piece(b).
part(b,p(0,0,0)).
part(b,p(1,0,0)).
part(b,p(1,0,1)).
part(b,p(2,0,0)).
part(b,p(2,1,0)).


piece(c).
part(c,p(0,0,0)).
part(c,p(1,0,0)).
part(c,p(1,0,1)).
part(c,p(1,1,1)).
part(c,p(2,1,1)).

piece(d).
part(d,p(0,0,0)).
part(d,p(1,0,0)).
part(d,p(2,0,0)).
part(d,p(2,1,0)).
part(d,p(2,1,1)).



piece(e).
part(e,p(0,0,0)).
part(e,p(1,0,0)).
part(e,p(0,1,0)).
part(e,p(0,1,1)).


piece(f).
part(f,p(0,0,0)).
part(f,p(1,0,0)).
part(f,p(1,1,0)).
part(f,p(1,1,1)).
