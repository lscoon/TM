#Q = {q0,q1,q2,q3,q4,q5,q6,q7,q8,q9,q10}
#S = {1,x,=}
#T = {1,x,=,X,Y,Z,_}
#q0 = q0
#B = _
#F = {q10}

; State q0, read 1m
q0 1 X r q1
q0 X X r q0
q0 x x r q8

; State q1, read 1 in 1m
q1 1 1 r q1
q1 x x r q2

;State q2, read 1n
q2 1 Y r q3
q2 Y Y r q2
q2 = = l q6

;State q3, read 1 in 1n
q3 1 1 r q3
q3 = = r q4

;State q4, read 1mn and turn back direction
q4 Z Z r q4
q4 1 Z l q5

;State q5, return to 1x1 position
q5 1 1 l q5
q5 Y Y l q5
q5 = = l q5
q5 Z Z l q5
q5 x x r q2

;State q6, change all Y to 1
q6 Y 1 l q6
q6 x x l q7

;State q7, return left most B
q7 1 1 l q7
q7 X X l q7
q7 _ _ r q0

;State q8, from x1 to =1
q8 1 1 r q8
q8 = = r q9

;State q9, from = to end
q9 Z Z r q9
q9 _ _ l q10






