#Q = {q0,q1,q2,q3,q4,q5,q6,q7,q8,q9}
#S = {a,b}
#T = {a,b,X,Y,_}
#q0 = q0
#B = _
#F = {q9}

;State q0, judge middle string
q0 a X r q1
q0 b Y r q1
q0 X X l q4
q0 Y Y l q4

;State q1, find first a or b
q1 a a r q1
q1 b b r q1
q1 X X l q2
q1 Y Y l q2
q1 _ _ l q2

;State q2, find last a or b
q2 a X l q3
q2 b Y l q3

;State q3, return to the left most
q3 a a l q3
q3 b b l q3
q3 X X r q0
q3 Y Y r q0

;State q4, force first half string from X/Y to a/b
q4 X a l q4
q4 Y b l q4
q4 _ _ r q5

;State q5, judge if equal
q5 a X r q6
q5 b Y r q7
q5 _ _ l q9

;State q6, check if a is equal
q6 a a r q6
q6 b b r q6
q6 _ _ r q6
q6 X _ l q8

;State q7, check if b is equal
q7 a a r q7
q7 b b r q7
q7 _ _ r q7
q7 Y _ l q8

;State q8, return to next check
q8 a a l q8
q8 b b l q8
q8 _ _ l q8
q8 X _ r q5
q8 Y _ r q5


