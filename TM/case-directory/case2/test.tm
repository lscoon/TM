#Q = {q0,q1,q2,q3,q4,q5,q6,q7,q8,q9,,preacc,preacc0,prerej,prerej0,accept,accept2,accept3,accept4,halt_accept,reject,reject2,reject3,reject4,reject5,halt_reject}
#S = {a,b}
#T = {a,b,X,Y,_,T,r,u,e,F,a,l,s}
#q0 = q0
#B = _
#F = {halt_accept}

;State q0, judge middle string
q0 a X r q1
q0 b Y r q1
q0 X X l q4
q0 Y Y l q4
q0 * * * prerej

;State q1, find first a or b
q1 a a r q1
q1 b b r q1
q1 X X l q2
q1 Y Y l q2
q1 _ _ l q2
q1 * * * prerej

;State q2, find last a or b
q2 a X l q3
q2 b Y l q3
q2 * * * prerej

;State q3, return to the left most
q3 a a l q3
q3 b b l q3
q3 X X r q0
q3 Y Y r q0
q3 * * * prerej

;State q4, force first half string from X/Y to a/b
q4 X a l q4
q4 Y b l q4
q4 _ _ r q5
q4 * * * prerej

;State q5, judge if equal
q5 a X r q6
q5 b Y r q7
q5 _ _ l q9
q5 * * * prerej

;State q6, check if a is equal
q6 a a r q6
q6 b b r q6
q6 _ _ r q6
q6 X _ l q8
q6 * * * prerej

;State q7, check if b is equal
q7 a a r q7
q7 b b r q7
q7 _ _ r q7
q7 Y _ l q8
q7 * * * prerej

;State q8, return to next check
q8 a a l q8
q8 b b l q8
q8 _ _ l q8
q8 X _ r q5
q8 Y _ r q5
q8 * * * prerej

q9 * * * preacc

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



