#Q = {q0,q1,q2,q3,q4,q5,q6,q7,q8,q9,q10,preacc,preacc0,prerej,prerej0,accept,accept2,accept3,accept4,halt_accept,reject,reject2,reject3,reject4,reject5,halt_reject}
#S = {1,x,=}
#T = {1,x,=,X,Y,Z,_,T,r,u,e,F,a,l,s}
#q0 = q0
#B = _
#F = {halt_accept}

; State q0, read 1m
q0 1 X r q1
q0 X X r q0
q0 x x r q8
q0 * * * prerej

; State q1, read 1 in 1m
q1 1 1 r q1
q1 x x r q2
q1 * * * prerej

;State q2, read 1n
q2 1 Y r q3
q2 Y Y r q2
q2 = = l q6
q2 * * * prerej

;State q3, read 1 in 1n
q3 1 1 r q3
q3 = = r q4
q3 * * * prerej

;State q4, read 1mn and turn back direction
q4 Z Z r q4
q4 1 Z l q5
q4 * * * prerej

;State q5, return to 1x1 position
q5 1 1 l q5
q5 Y Y l q5
q5 = = l q5
q5 Z Z l q5
q5 x x r q2
q5 * * * prerej

;State q6, change all Y to 1
q6 Y 1 l q6
q6 x x l q7
q6 * * * prerej

;State q7, return left most B
q7 1 1 l q7
q7 X X l q7
q7 _ _ r q0
q7 * * * prerej

;State q8, from x1 to =1
q8 1 1 r q8
q8 = = r q9
q8 * * * prerej

;State q9, from = to end
q9 Z Z r q9
q9 _ _ l q10
q9 * * * prerej

q10 * * * preacc

preacc * * r preacc
preacc _ _ l preacc0
preacc0 * _ l preacc0
preacc0 _ _ r accept
accept * T r accept2
accept2 * r r accept3
accept3 * u r accept4
accept4 * e * halt_accept

prerej * * r prerej
prerej _ _ l prerej0
prerej0 * _ l prerej0
prerej0 _ _ r reject
reject * _ l reject
reject _ F r reject2
reject2 _ a r reject3
reject3 _ l r reject4
reject4 _ s r reject5
reject5 * e * halt_reject




