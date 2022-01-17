;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |HW #12|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; EXERCISE 1:

; positions: (X) X [List-of X] [X -> Boolean] -> [List-of Nat]
; returns the list of positions in lox where x occurs

(check-expect (positions 3 (list 1 2 3 3) =) (list 2 3))
(check-expect (positions "hi" (list "hi" "hello" "hey" "hi") string=?) (list 0 3))
(check-expect (positions 123 '() =) '())
(check-expect (positions 3 (list 1 2 4 5) =) '())

(define (positions x lox x=?) 
  (cond [(empty? lox) '()]
        [(cons? lox) (if (x=? (first lox) x)
                         (cons 0 (add-1 (positions x (rest lox) x=?)))
                         (add-1 (positions x (rest lox) x=?)))]))

; add-1 : [List-of Nat] -> [List-of Nat]
; adds 1 to every element in the list (basically map)

(check-expect (add-1 '()) '())
(check-expect (add-1 (list 1 2 3)) (list 2 3 4))
(check-expect (add-1 (list 1)) (list 2))

(define (add-1 lon)
  (cond [(empty? lon) '()]
        [(cons? lon) (cons (add1 (first lon))
                           (add-1 (rest lon)))]))

; positions.v2 : (X)(Y) X [List-of X] [X -> Boolean] -> [List-of Nat]
; returns the list of positions in lox where x occurs

(check-expect (positions.v2 3 (list 1 2 3 3) =) (list 2 3))
(check-expect (positions.v2 "hi" (list "hi" "hello" "hey" "hi") string=?) (list 0 3))
(check-expect (positions.v2 123 '() =) '())
(check-expect (positions.v2 3 (list 1 2 4 5) =) '())

(define (positions.v2 x lox x=?)
  (local [; positions/acc : (X)(Y) [List-of X] [List-of X] [X->Boolean] -> [List-of Nat]
          ; same purpose, but using an accumulator: list so far of position of x in the list
          ; (check-expect (positions/acc 3 (list 1 2 3 3) =) (list 2 3))
          ; (check-expect (positions/acc 123 '() =) '())
          (define (positions/acc x lox x=? count acc)
            (cond [(empty? lox) acc]
                  [(cons? lox)
                   (if (x=? x (first lox))
                       (positions/acc x (rest lox) x=? (add1 count) (cons count acc))
                       (positions/acc x (rest lox) x=? (add1 count) acc))]))]
    (reverse (positions/acc x lox x=? 0 '()))))

; positions.v2 is more efficient because it does not need to recur through
; the list every single time. Instead, it can keep track of the current
; postion and the list of the postions so far.

; EXERCISE 3:

(require 2htdp/image)
(require 2htdp/universe)

(define BACKGROUND (empty-scene 1000 500 "white"))
(define FSIZE-EDIT   20) ; the default text font size (can be changed at runtime)
(define COLOR-EDIT   "black")
(define DEFAULT-FONT-SIZE 1)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; GENERAL UTILITY FUNCTIONS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; last : [X] [NEList-of X] -> X
; the last element of a non-empty list
(check-expect (last (list 1 2 3 4)) 4)
(check-expect (last (list 1))       1)
(define (last lox)
  (list-ref lox (sub1 (length lox))))

; subst : [X] X Nat [List-of X] -> [List-of X]
; substitute new for the element at pos in lox (error if does not exist)
(check-expect (subst "!" 0 (list 1 2 3)) (list "!" 2 3))
(check-expect (subst "?" 3 (list 0 1 2 3 4 5 6)) (list 0 1 2 "?" 4 5 6))
(define (subst new pos lox)
  (if (= pos 0)
      (cons new (rest lox))
      (cons (first lox) (subst new (sub1 pos) (rest lox)))))

; subst-two : [X] X X Nat [List-of X] -> [List-of X]
; substitute the *two* elements new1 and new2 for the element at pos (error if does not exist in lox)
(check-expect (subst-two 1 2 0 '(0)) (list 1 2))
(check-expect (subst-two 3 4 3 '(0 1 2 ? 5 6 7)) (list 0 1 2 3 4 5 6 7))
(define (subst-two new1 new2 pos lox)
  (if (= pos 0)
      (cons new1 (cons new2 (rest lox)))
      (cons (first lox) (subst-two new1 new2 (sub1 pos) (rest lox)))))

; subst-for-two : [X] X Nat [List-of X]-> [List-of X]
; substitute new for the *two* elements as positions pos and pos+1 (error if they do not exist in lox)
(check-expect (subst-for-two "X" 0 (list 0 1)) (list "X"))
(check-expect (subst-for-two "X" 2 (list 0 1 2 3 4 5 6)) (list 0 1 "X" 4 5 6))
(define (subst-for-two new pos lox)
  (if (= pos 0)
      (cons new (rest (rest lox)))
      (cons (first lox) (subst-for-two new (sub1 pos) (rest lox)))))

; char-insert : 1String Nat String -> String
; insert character c into s at position pos, or at end if pos = (string-length s)
(check-expect (char-insert "X" 0 "") "X")
(check-expect (char-insert "X" 0 "A") "XA")
(check-expect (char-insert "X" 2 "AB") "ABX")
(define (char-insert c pos s)
  (string-append (substring s 0 pos)
                 c
                 (substring s pos)))

; char-delete : Nat String -> String
; delete the character at the given position (error if does not exist)
(check-expect (char-delete 0 "Hello") "ello")
(check-expect (char-delete 2 "Hello") "Helo")
(define (char-delete pos s)
  (string-append (substring s 0 pos)
                 (substring s (add1 pos))))

; stack-images-left-aligned : [List-of Image] -> Image
; stack the images on top of each other, aligned on the left
(check-expect (stack-images-left-aligned '()) empty-image)
(check-expect (stack-images-left-aligned (list (text "A" 12 "black")
                                               (text "ABC" 13 "red")))
              (above/align "left"
                           (text "A" 12 "black")
                           (text "ABC" 13 "red")))
(define (stack-images-left-aligned loi)
  (local [(define (stack-image image stack)
            (above/align "left" image stack))]
    (foldr stack-image empty-image loi)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; DATA DEFINITIONS FOR A TEXT BUFFER
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; A Line is a String
; A line of text contained in some text buffer.
; No special characters (such as newlines) allowed.
(define line-0 "When I came back from Munich,")
(define (line-templ l)
  (... l ...))

(define-struct buffer [lol lnum cnum fsize mode])

; A Buffer is a (make-buffer [NEList-of Line] Nat Nat PosInt String)
; a buffer of a text editor:
; - lol is the list of lines of the text.
;   This list is NEVER empty (see buffer-text-empty below)
; - lnum is the  line  number where the cursor is. Range: 0 .. len(lol)-1
; - cnum is the column number where the cursor is. Range: 0 .. len(s) . s = string at line lnum.
; - fsize is the size of the font
; - mode is one of:
; - "EDIT" : when the user is editing the buffer text
; - "MENU" : when a help menu is being displayed
; - "SEARCH" : when the buffer is in search mode

(define buffer-text-empty (make-buffer (list "")                0 0 FSIZE-EDIT "EDIT"))
(define buffer-text       (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "EDIT"))
(define buffer-menu       (make-buffer (list "Line 0") 0 4 30 "MENU"))
(define buffer-search     (make-buffer (list "Line 0" "Line 1") 0 5 10 "SEARCH"))

(define (buffer-templ buffer)
  (... (lol-templ (buffer-lol buffer))
       (buffer-lnum  buffer)
       (buffer-cnum  buffer)
       (buffer-fsize buffer)
       (buffer-mode buffer)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; UTILITY FUNCTIONS OPERATING ON A BUFFER
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; start-of-buffer? : Buffer -> Boolean
; are we at the top-left corner of the buffer?
(check-expect (start-of-buffer? buffer-text-empty) #t)
(check-expect (start-of-buffer? buffer-text)       #f)
(define (start-of-buffer? buffer)
  (= (buffer-lnum buffer) (buffer-cnum buffer) 0))

; end-of-buffer? : Buffer -> Boolean
; are we at the lower right corner of the buffer, i.e. last line, last column?
(check-expect (end-of-buffer? buffer-text-empty) #t)
(check-expect (end-of-buffer? buffer-text)       #f)
(define (end-of-buffer? buffer)
  (local [(define lol (buffer-lol buffer))]
    (and (= (buffer-lnum buffer) (sub1 (length lol)))
         (= (buffer-cnum buffer) (string-length (last lol))))))

; end-of-line : Buffer Nat -> Nat
; the end-of-line position of the given line in the buffer
(check-expect (end-of-line buffer-text-empty 0) 0)
(check-expect (end-of-line buffer-text       1) 6)
(define (end-of-line buffer lnum)
  (local [(define lol  (buffer-lol buffer))
          (define line (list-ref lol lnum))]
    (string-length line)))

; end-of-line? : Buffer -> Boolean
; are we at the end of the current line in the buffer?
(check-expect (end-of-line? buffer-text-empty) #t)
(check-expect (end-of-line? buffer-text)       #f)
(define (end-of-line? buffer)
  (local [(define lnum (buffer-lnum buffer))
          (define cnum (buffer-cnum buffer))]
    (= cnum (end-of-line buffer lnum))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; MAIN: THE BIG-BANG WORLD PROGRAM
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; editor : String -> Text
; Launches the text editor. The input string is currently ignored
; Returns the buffer contents as a Text.
(define (editor initial-text)
  (return-text (big-bang (make-buffer (text->lol initial-text) 0 0 FSIZE-EDIT "EDIT")
                 [name    "A Simple Text Editor"]
                 [to-draw draw-buffer]
                 [on-key  process-key])))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; DRAWING THE BUFFER
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; draw-buffer : Buffer -> Image
; render the buffer in text- or cmd mode, as the case may be

(check-expect (draw-buffer buffer-text-empty) (draw-buffer-text-mode buffer-text-empty))
(check-expect (draw-buffer (make-buffer (list "") 0 0 DEFAULT-FONT-SIZE "MENU"))
              (process-menu (make-buffer (list "") 0 0 DEFAULT-FONT-SIZE "MENU")))
(check-expect (draw-buffer (make-buffer (list "") 0 0 DEFAULT-FONT-SIZE "SEARCH"))
              (process-search (make-buffer (list "") 0 0 DEFAULT-FONT-SIZE "SEARCH")))

(define (draw-buffer buffer)
  (cond [(string=? (buffer-mode buffer) "EDIT") (draw-buffer-text-mode buffer)]
        [(string=? (buffer-mode buffer) "MENU") (process-menu buffer)]
        [(string=? (buffer-mode buffer) "SEARCH") (process-search buffer)]))

; draw-buffer-text-mode : Buffer -> Image
; render a buffer: the buffer text
(check-expect (draw-buffer-text-mode buffer-text-empty)
              (place-image/align (char->image-cursor " " FSIZE-EDIT)
                                 0 0 "left" "top" BACKGROUND))
(define (draw-buffer-text-mode buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))]
    (place-image/align (stack-images-left-aligned (lines->images lol lnum cnum fsize))
                       0 0 "left" "top"
                       BACKGROUND)))

; lines->images : [NEList-of Line] Nat Nat PosInt -> [List-of Image]
; turn a list of lines into a list of images containing the text

(check-expect (lines->images (list "Line of text" "") 1 0 FSIZE-EDIT)
              (list (line->image "Line of text" #f FSIZE-EDIT)
                    (char->image-cursor " " FSIZE-EDIT)))
(check-expect (lines->images (list "Line of text1" "Line of text2") 0 4 FSIZE-EDIT)
              (list (line->image-cursor "Line of text1" 4 FSIZE-EDIT)
                    (line->image "Line of text2" #f FSIZE-EDIT)))

(define (lines->images lol lnum cnum fsize)
  (if (= lnum 0)
      (cons (line->image-cursor (first lol) cnum fsize)
            (map (lambda (l) (line->image l #f fsize)) (rest lol)))
      (cons (line->image (first lol) #f fsize)
            (lines->images (rest lol) (sub1 lnum) cnum fsize))))
        
; line->image : Line Boolean PosInt -> Image
; the given text rendered as an image
(check-expect (line->image "Line of text" #f FSIZE-EDIT)
              (text/font "Line of text" FSIZE-EDIT COLOR-EDIT "Monospace" "default"
                         "normal" "normal" #f))
(define (line->image line underline? fsize)
  (text/font line fsize COLOR-EDIT "Monospace" "default" "normal" "normal" underline?))

; line->image-cursor : Line Nat PosInt -> Image
; the given text rendered as an image in which the char at the given position is underlined
(check-expect (line->image-cursor "Line of text" 0 FSIZE-EDIT)
              (beside (text/font "L"           FSIZE-EDIT COLOR-EDIT "Monospace" "default"
                                 "normal" "normal" #t)
                      (text/font "ine of text" FSIZE-EDIT COLOR-EDIT "Monospace" "default"
                                 "normal" "normal" #f)))
(define (line->image-cursor line cnum fsize)
  (if (< cnum (string-length line))
      (beside (line->image (substring line 0 cnum) #f fsize)
              (char->image-cursor (string-ith line cnum) fsize)
              (line->image (substring line (add1 cnum)) #f fsize))
      ; special case: cursor is post-end-of-line
      (beside (line->image line #f fsize)
              (char->image-cursor " " fsize))))

; char->image-cursor : 1String Nat String PosInt -> Image
; the 1-character string rendered as an image of an underlined character
(check-expect (char->image-cursor "X" FSIZE-EDIT)
              (text/font "X" FSIZE-EDIT COLOR-EDIT "Monospace" "default" "normal" "normal" #t))
(define (char->image-cursor c fsize)
  (text/font c fsize COLOR-EDIT "Monospace" "default" "normal" "normal" #t))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; RESPONDING TO KEY EVENTS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; the following keys are ignored
; IGNORE-KEYS-EDIT : -> [List-of Key]
(define IGNORE-KEYS-EDIT
  (list
   "\t"                  ; tab
   "\u007F"              ; delete key
   "shift" "rshift"      ; typing "shift-a" still results in an "A" being processed
   "control" "rcontrol"
   "menu"
   "f4" "f5" "f6" "f7" "f8" "f9" "f10" "f11" "f12"
   "numlock"
   "scroll"
   "wheel-up" "wheel-down" "wheel-left" "wheel-right")) ; "mouse" key events

; ignore-key? : Key [List-of Key] -> Boolean
; is the key to be ignored, i.e. a member of the given key list?
(check-expect (ignore-key? "menu" IGNORE-KEYS-EDIT) #t)
(check-expect (ignore-key? "A"    IGNORE-KEYS-EDIT) #f)
(define (ignore-key? key lok)
  (ormap (lambda (ik) (key=? ik key)) lok))

; process-key : Buffer Key -> Buffer
; respond to each key event
(check-expect (process-key buffer-text "left") (process-text-left buffer-text))
(define (process-key buffer key)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define mode  (buffer-mode  buffer))
          (define fsize (buffer-fsize buffer))]
    (process-key-text-mode buffer key)))

; process-key-text-mode : Buffer Key -> Buffer
; respond to each key event in text mode

(check-expect (process-key-text-mode buffer-text "left") (process-text-left buffer-text))
(check-expect (process-key-text-mode buffer-text "\t")      buffer-text)
(check-expect (process-key-text-mode buffer-text "left")   (process-text-left buffer-text))
(check-expect (process-key-text-mode buffer-text "right")  (process-text-right buffer-text))
(check-expect (process-key-text-mode buffer-text "up")     (process-text-up buffer-text))
(check-expect (process-key-text-mode buffer-text "down")   (process-text-down buffer-text))
(check-expect (process-key-text-mode buffer-text "home")   (process-text-home buffer-text))
(check-expect (process-key-text-mode buffer-text "end")    (process-text-end buffer-text))
(check-expect (process-key-text-mode buffer-text "\r")     (process-text-return buffer-text))
(check-expect (process-key-text-mode buffer-text "\b")     (process-text-backspace buffer-text))
(check-expect (process-key-text-mode buffer-text "f1")     (process-text-font-inc buffer-text))
(check-expect (process-key-text-mode buffer-text "f2")     (process-text-font-dec buffer-text))
(check-expect (process-key-text-mode buffer-text "escape") (process-text-menu buffer-text))
(check-expect (process-key-text-mode buffer-text "a")      (process-text-printable buffer-text "a"))
(check-expect (process-key-text-mode buffer-text "#")      (process-text-printable buffer-text "#"))
(check-expect (process-key-text-mode buffer-text ":")      (process-text-printable buffer-text ":"))
(check-expect (process-key-text-mode buffer-text "9")      (process-text-printable buffer-text "9"))
(check-expect (process-key-text-mode buffer-text "f3")     (process-text-search buffer-text))
(check-expect (process-key-text-mode
               (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU") "escape")
              (process-text-menu (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU")))
(check-expect (process-key-text-mode
               (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU") "5")
              (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU"))
(check-expect (process-key-text-mode
               (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "SEARCH") "5")
              (new-search "5" (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "SEARCH")))
               
(define (process-key-text-mode buffer key)
  (cond [(string=? (buffer-mode buffer) "EDIT")
         (cond [(ignore-key? key IGNORE-KEYS-EDIT) buffer]
               [(key=? key "left")   (process-text-left      buffer)]
               [(key=? key "right")  (process-text-right     buffer)]
               [(key=? key "up")     (process-text-up        buffer)]
               [(key=? key "down")   (process-text-down      buffer)]
               [(key=? key "home")   (process-text-home      buffer)]
               [(key=? key "end")    (process-text-end       buffer)]
               [(key=? key "\r")     (process-text-return    buffer)]
               [(key=? key "\b")     (process-text-backspace buffer)]
               [(key=? key "f1")     (process-text-font-inc  buffer)]
               [(key=? key "f2")     (process-text-font-dec  buffer)]
               [(key=? key "escape") (process-text-menu      buffer)]
               [(key=? key "f3")     (process-text-search    buffer)]
               [else                 (process-text-printable buffer key)])]
        [(string=? (buffer-mode buffer) "MENU")
         (cond [(key=? key "escape") (process-text-menu buffer)]
               [else buffer])]
        [(string=? (buffer-mode buffer) "SEARCH") (new-search key buffer)]))

; process-text-left : Buffer -> Buffer
; process the "left" cursor key: move left, possibly up one line

(check-expect (process-text-left buffer-text) (make-buffer (list "Line 0" "Line 1") 1 2
                                                           FSIZE-EDIT "EDIT"))
(check-expect (process-text-left buffer-text-empty) buffer-text-empty)
(check-expect (process-text-left (make-buffer (list "Line 0" "Line 1") 2 0
                                              FSIZE-EDIT "EDIT"))
              (make-buffer (list "Line 0" "Line 1") 1 6 FSIZE-EDIT "EDIT"))

(define (process-text-left buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))]
    (cond [(start-of-buffer? buffer) buffer]
          [(= cnum 0)                (make-buffer lol (sub1 lnum) (end-of-line buffer (sub1 lnum))
                                                  fsize "EDIT")]
          [else                      (make-buffer lol lnum (sub1 cnum) fsize "EDIT")])))

; process-text-right : Buffer -> Buffer
; process the "right" cursor key: move right, possibly down one line

(check-expect (process-text-right buffer-text) (make-buffer (list "Line 0" "Line 1") 1 4
                                                            FSIZE-EDIT "EDIT"))
(check-expect (process-text-right buffer-text-empty) buffer-text-empty)
(check-expect (process-text-right (make-buffer (list "Line 0" "Line 1") 0 6
                                               FSIZE-EDIT "EDIT"))
              (make-buffer (list "Line 0" "Line 1") 1 0 FSIZE-EDIT "EDIT"))

(define (process-text-right buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))]
    (cond [(end-of-buffer? buffer) buffer]
          [(end-of-line?   buffer) (make-buffer lol (add1 lnum) 0 fsize "EDIT")]
          [else                    (make-buffer lol lnum (add1 cnum) fsize "EDIT")])))

; process-text-up : Buffer -> Buffer
; process the "up" cursor key

(check-expect (process-text-up buffer-text-empty) buffer-text-empty)
(check-expect (process-text-up buffer-text) (make-buffer (list "Line 0" "Line 1") 0 3
                                                         FSIZE-EDIT "EDIT"))
(define (process-text-up buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define prev-lnum (sub1 lnum))]
    (if (= lnum 0)
        buffer
        (make-buffer lol
                     prev-lnum
                     (min cnum (end-of-line buffer prev-lnum))
                     fsize
                     "EDIT"))))

; process-text-down : Buffer -> Buffer
; process the "down" cursor key

(check-expect (process-text-down buffer-text-empty) buffer-text-empty)
(check-expect (process-text-down (make-buffer (list "Line 0" "Line 1") 0 3
                                              FSIZE-EDIT "EDIT"))
              buffer-text)

(define (process-text-down buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define next-lnum (add1 lnum))]
    (if (= lnum (sub1 (length lol)))
        buffer
        (make-buffer lol
                     next-lnum
                     (min cnum (end-of-line buffer next-lnum))
                     fsize
                     "EDIT"))))

; process-text-home : Buffer -> Buffer
; process the "home" key
(check-expect (process-text-home buffer-text-empty) buffer-text-empty)
(check-expect (process-text-home buffer-text) (make-buffer (list "Line 0" "Line 1") 1 0
                                                           FSIZE-EDIT "EDIT"))
(define (process-text-home buffer)
  (local [(define lol      (buffer-lol buffer))
          (define lnum     (buffer-lnum buffer))
          (define cnum     (buffer-cnum buffer))
          (define fsize    (buffer-fsize buffer))
          (define new-lnum (if (= cnum 0) 0 lnum))
          (define new-cnum 0)]
    (make-buffer lol
                 new-lnum
                 new-cnum
                 fsize
                 "EDIT")))

; process-text-end : Buffer -> Buffer
; process the "end" key
(check-expect (process-text-end buffer-text-empty) buffer-text-empty)
(check-expect (process-text-end buffer-text) (make-buffer (list "Line 0" "Line 1") 1 6
                                                          FSIZE-EDIT "EDIT"))
(define (process-text-end buffer)
  (local [(define lol  (buffer-lol  buffer))
          (define lnum (buffer-lnum buffer))
          (define cnum (buffer-cnum buffer))
          (define fsize (buffer-fsize buffer))
          (define new-lnum (if (end-of-line? buffer) (sub1 (length lol)) lnum))
          (define new-cnum (end-of-line buffer new-lnum))]
    (make-buffer lol
                 new-lnum
                 new-cnum
                 fsize
                 "EDIT")))

; process-return : Buffer -> Buffer
; process the "return" key, as follows:
; 1. replace current line by the current line up to and excluding the current position
; 2. insert a new line after the current one,
;    consisting of the current line from the current position
; 3. lnum++, cnum = 0
(check-expect (process-text-return buffer-text)
              (make-buffer (list "Line 0" "Lin" "e 1") 2 0 FSIZE-EDIT "EDIT"))
(define (process-text-return buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define line (list-ref lol lnum))
          (define new-line-1 (substring line 0 cnum))
          (define new-line-2 (substring line cnum))
          (define new-lol (subst-two new-line-1 new-line-2 lnum lol))]
    (make-buffer new-lol (add1 lnum) 0 fsize "EDIT")))

; process-text-backspace : Buffer -> Buffer
; process the "backspace" key, as follows:
; 1. if at the start of the buffer, do nothing.
; 2. if column > 0: just delete the current character and move left.
; 3. if column = 0: replace the lines at positions lnum-1 and lnum by their concatenation
;    Note that case 3 precisely reverses the effect of the <RETURN> key

(check-expect (process-text-backspace (make-buffer (list "Line 0" "Lin" "e 1") 2 0
                                                   FSIZE-EDIT "EDIT")) buffer-text)
(check-expect (process-text-backspace buffer-text) (make-buffer (list "Line 0" "Lie 1") 1 2
                                                                FSIZE-EDIT "EDIT"))
(check-expect (process-text-backspace (make-buffer (list "Line 0" "Line 1") 0 0 FSIZE-EDIT "EDIT"))
              (make-buffer (list "Line 0" "Line 1") 0 0 FSIZE-EDIT "EDIT"))

(define (process-text-backspace buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define line  (list-ref lol lnum))]
    (cond [(start-of-buffer? buffer) buffer]
          [(> cnum 0) (make-buffer
                       (subst (char-delete (sub1 cnum) line) lnum lol)
                       lnum (sub1 cnum) fsize "EDIT")]
          [(= cnum 0) (make-buffer
                       (subst-for-two (string-append (list-ref lol (sub1 lnum))
                                                     (list-ref lol lnum))
                                      (sub1 lnum)
                                      lol)
                       (sub1 lnum)
                       (end-of-line buffer (sub1 lnum))
                       fsize
                       "EDIT")])))

; process-text-printable : Buffer Key -> Buffer
; process the key event corresponding to a printable key: just insert it
(check-expect (process-text-printable buffer-text       "!")
              (make-buffer (list "Line 0" "Lin!e 1") 1 4 FSIZE-EDIT "EDIT"))
(check-expect (process-text-printable buffer-text-empty "!")
              (make-buffer (list "!") 0 1 FSIZE-EDIT "EDIT"))
(define (process-text-printable buffer key)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define line  (list-ref lol lnum))]
    (make-buffer (subst (char-insert key cnum line) lnum lol)
                 lnum (add1 cnum) fsize "EDIT")))

; process-text-font-inc : Buffer -> Buffer
; process the "f1" key, which will increase the font size

(check-expect (process-text-font-inc buffer-text) (make-buffer (list "Line 0" "Line 1") 1 3
                                                               21 "EDIT"))
(check-expect (process-text-font-inc buffer-text-empty) (make-buffer (list "") 0 0 21 "EDIT"))

(define (process-text-font-inc buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define line  (list-ref lol lnum))]
    (make-buffer lol lnum cnum (add1 fsize) "EDIT")))

; process-text-font-dec : Buffer -> Buffer
; process the "f2" key, which will decrease the font size

(check-expect (process-text-font-dec buffer-text) (make-buffer (list "Line 0" "Line 1") 1 3
                                                               19 "EDIT"))
(check-expect (process-text-font-dec buffer-text-empty) (make-buffer (list "") 0 0 19 "EDIT"))
(check-expect (process-text-font-dec
               (make-buffer (list "Line 0" "Line 1") 1 3 DEFAULT-FONT-SIZE "EDIT"))
              (make-buffer (list "Line 0" "Line 1") 1 3 DEFAULT-FONT-SIZE "EDIT"))

(define (process-text-font-dec buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define line  (list-ref lol lnum))]
    (if (= fsize DEFAULT-FONT-SIZE)
        buffer
        (make-buffer lol lnum cnum (sub1 fsize) "EDIT"))))

(define MENU-TEXT (list "Modes : EDIT MENU SEARCH"
                        "Press ESCAPE to toggle between MENU and EDIT."
                        "Press F3 to toggle to SEARCH mode and again to execute search string."
                        "To increase font size or decrease font size, press F1 and F2."))

(define FONT-SIZE 20)
(define FONT-COLOR "blue")
                   
; process-text-menu : Buffer -> Buffer
; allows user to toggle modes EDIT and MENU

(check-expect (process-text-menu buffer-text)
              (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU"))
(check-expect (process-text-menu buffer-text-empty)
              (make-buffer (list "") 0 0 FSIZE-EDIT "MENU"))

(define (process-text-menu buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define mode  (buffer-mode  buffer))
          (define line  (list-ref lol lnum))]
    (cond [(string=? mode "EDIT")
           (make-buffer lol lnum cnum fsize "MENU")]
          [else (make-buffer lol lnum cnum fsize "EDIT")])))

; process-menu : Buffer -> Image
; Draws new buffer in "MENU" mode

(check-expect (process-menu (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU"))
              (place-image/align (menu-image MENU-TEXT)
                                 500 250 "center" "center"
                                 BACKGROUND))
(check-expect (process-menu buffer-text-empty)
              (place-image/align (menu-image MENU-TEXT)
                                 500 250 "center" "center"
                                 BACKGROUND))

(define (process-menu buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define mode  (buffer-mode  buffer))
          (define line  (list-ref lol lnum))]
    (place-image/align (menu-image MENU-TEXT)
                       500 250 "center" "center"
                       BACKGROUND)))

; menu-image : [List-of String] -> Image
; converts a list of strings into an image

(check-expect (menu-image (list "hi" "hello")) (above
                                                (text/font "hi" FONT-SIZE FONT-COLOR "Monospace"
                                                           "default" "normal" "normal" #f)
                                                (text/font "hello" FONT-SIZE FONT-COLOR "Monospace"
                                                           "default" "normal" "normal" #f)))
(check-expect (menu-image MENU-TEXT)
              (above
               (text/font "Modes : EDIT MENU SEARCH" 
                          FONT-SIZE FONT-COLOR "Monospace" "default" "normal" "normal" #f)
               (text/font "Press ESCAPE to toggle between MENU and EDIT."
                          FONT-SIZE FONT-COLOR "Monospace" "default" "normal" "normal" #f)
               (text/font "Press F3 to toggle to SEARCH mode and again to execute search string."
                          FONT-SIZE FONT-COLOR "Monospace" "default" "normal" "normal" #f)
               (text/font "To increase font size or decrease font size, press F1 and F2."
                          FONT-SIZE FONT-COLOR "Monospace" "default" "normal" "normal" #f)))
              
                                                
(define (menu-image los)
  (foldr above empty-image (map (lambda (s) (text/font s FONT-SIZE FONT-COLOR
                                                       "Monospace" "default" "normal"
                                                       "normal" #f)) los)))
 
(define LINE-SEP "\n")

; A Text is a String
; represents the entire text contained in some text buffer where lines are separated by LINE-SEP

(define text-0
  (string-append
   "About a hiring visit by Edsger Dijkstra (1930-2002)," LINE-SEP
   "a famous computer scientist:"                         LINE-SEP))

(define text-1
  (string-append
   "I" LINE-SEP
   "AM" LINE-SEP
   "IRONMAN" LINE-SEP))

(define (text-templ t)
  (... t ...))


; split : String 1-String -> [List-of String]
; splits a string using c as the separator

(check-expect (split "A|B|C|D"   "|") (list "A" "B" "C" "D"))
(check-expect (split "|A|B|C|D"  "|") (list "" "A" "B" "C" "D"))
(check-expect (split "|A|B|C|D|" "|") (list "" "A" "B" "C" "D" ""))
(check-expect (split "A"         "|") (list "A"))
(check-expect (split ""          "|") (list ""))
(check-expect (split "|"         "|") (list "" ""))
(check-expect (split "||"        "|") (list "" "" ""))

(define (split s c)
  (local [; add-string : 1-String 1-String -> String
          ; combines characters into a substring up until c
          ; if "A" and "|" and c = "|" : (check-expect (add-string "A" "|") "A")
          ; if "" and "A" and c = "|" : (check-expect (add-string "" "A") " A")
          (define (add-string new-string combined-list)
            (if (string=? new-string c)
                (append (list "") combined-list)
                (cons (string-append new-string (first combined-list)) (rest combined-list))))]
    (foldr add-string (list "") (explode s))))

; text->lol : Text -> [NEList-of Line]
; converts a Text into a non-empty list of lines suitable for the buffer data structure

(check-expect (text->lol text-0) (list "About a hiring visit by Edsger Dijkstra (1930-2002),"
                                       "a famous computer scientist:"
                                       ""))
(check-expect (text->lol text-1) (list "I"
                                       "AM"
                                       "IRONMAN"
                                       ""))

(define (text->lol t)
  (split t LINE-SEP))

; lol->text : [NEList-of Line] -> Text
; "exports" the non-empty list of lines in the buffer as a Text

(check-expect (lol->text (list "Line 0" "Line 1")) (string-append "Line 0" LINE-SEP
                                                                  "Line 1" LINE-SEP))
(check-expect (lol->text (list "")) (string-append "" LINE-SEP))

(define (lol->text lol)
  (local [; convertor : String String -> String
          ; converts two strings into one string with line separators
          ; if s1 = "Hi" and s2 = "Hey" : (check-expect (convertor s1 s2) "Hi LINE-SEP Hey")
          ; if s1 = "" and s2 = "Yoooo" : (check-expect (convertor s1 s2) " LINE-SEP Yoooo")
          (define (convertor s1 s2)
            (string-append s1 LINE-SEP s2))]
    (foldr convertor "" lol)))

; return-text : Buffer -> Text

(define (return-text buffer)
  (lol->text (buffer-lol buffer)))

; EXERCISE 4:

(define IGNORE-KEYS-SEARCH
  (list
   "\t"                  ; tab
   "\u007F"              ; delete key
   "shift" "rshift"      ; typing "shift-a" still results in an "A" being processed
   "control" "rcontrol"
   "menu"
   "f1" "f2" "f4" "f5" "f6" "f7" "f8" "f9" "f10" "f11" "f12"
   "numlock"
   "scroll"
   "wheel-up" "wheel-down" "wheel-left" "wheel-right")) ; "mouse" key events

; process-text-search : Buffer Key -> Buffer
; allows user to toggle to SEARCH mode

(check-expect (process-text-search buffer-text)
              (make-buffer (list "" "Line 0" "Line 1") 1 3 FSIZE-EDIT "SEARCH"))
(check-expect (process-text-search buffer-text-empty)
              (make-buffer (list "" "") 0 0 FSIZE-EDIT "SEARCH"))

(define (process-text-search buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define mode  (buffer-mode  buffer))
          (define line  (list-ref lol lnum))]
    (insert-search-string (make-buffer lol lnum cnum fsize "SEARCH"))))

; insert-search-string : Buffer -> Buffer
; adds the search string line to the buffer and updates cursor location

(check-expect (insert-search-string buffer-search)
              (make-buffer (list "" "Line 0" "Line 1") 0 5 10 "SEARCH"))
(check-expect (insert-search-string (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "SEARCH"))
              (make-buffer (list "" "Line 0" "Line 1") 1 3 FSIZE-EDIT "SEARCH"))
 
(define (insert-search-string buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define mode  (buffer-mode  buffer))
          (define line  (list-ref lol lnum))]
    (make-buffer (cons "" lol) lnum cnum fsize "SEARCH")))

; new-search : Key Buffer -> Buffer
; draws the new buffer after a key press

(check-expect (new-search "wheel-up" buffer-search) buffer-search)
(check-expect (new-search "\r" buffer-search)
              (make-buffer (list "Line \n0" "Line 1") 0 6 10 "SEARCH"))
(check-expect (new-search "\b"  (make-buffer (list "Line 0") 0 0 FSIZE-EDIT "SEARCH"))
              (make-buffer (list "Line 0") 0 0 FSIZE-EDIT "SEARCH"))
(check-expect (new-search "f3" (make-buffer (list "Line 0" "Line 1" "Line 0") 1 0 10 "SEARCH"))
              (make-buffer (list "Line 1" "Line 0") 1 0 10 "EDIT"))
(check-expect (new-search "!" buffer-search) (printable-keys-search buffer-search "!"))
                         
(define (new-search key buffer)
  (if (ignore-key? key IGNORE-KEYS-SEARCH)
      buffer
      (cond [(key=? key "\r") (return-key-search buffer)]
            [(key=? key "\b") (backspace-key-search buffer)]
            [(key=? key "f3") (execute-key-search buffer)]
            [else (printable-keys-search buffer key)])))

; return-key-search : Buffer -> Buffer
; adds "\n" to the search string in a buffer

(check-expect (return-key-search buffer-search)
              (make-buffer (list "Line \n0" "Line 1") 0 6 10 "SEARCH"))
(check-expect (return-key-search (make-buffer (list "") 0 0 FSIZE-EDIT "SEARCH"))
              (make-buffer (list "\n") 0 1 FSIZE-EDIT "SEARCH"))

(define (return-key-search buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define line  (list-ref lol lnum))]
    (make-buffer (subst (char-insert "\n" cnum line) lnum lol)
                 lnum (add1 cnum) fsize "SEARCH")))

; backspace-key-search : Buffer -> Buffer
; processes the "backspace" key, as follows:
; 1. if at the start of the buffer, do nothing.
; 2. if column > 0: just delete the current character and move left.

(check-expect (backspace-key-search (make-buffer (list "Line 0") 0 0 FSIZE-EDIT "SEARCH"))
              (make-buffer (list "Line 0") 0 0 FSIZE-EDIT "SEARCH"))
(check-expect (backspace-key-search buffer-search)
              (make-buffer (list "Line0" "Line 1") 0 4 10 "SEARCH"))
              
(define (backspace-key-search buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define line  (list-ref lol lnum))]
    (cond [(start-of-buffer? buffer) buffer]
          [(> cnum 0) (make-buffer
                       (subst (char-delete (sub1 cnum) line) lnum lol)
                       lnum (sub1 cnum) fsize "SEARCH")])))

; execute-key-search : Buffer -> Buffer
; executes the search string

(check-expect (execute-key-search (make-buffer (list "Line 0" "Line 1" "Line 0") 1 0 10 "SEARCH"))
              (make-buffer (list "Line 1" "Line 0") 1 0 10 "EDIT"))
               
(define (execute-key-search buffer)
  (search buffer))
  

; printable-keys-search : Buffer Key -> Buffer
; process the key event corresponding to a printable key: just insert it

(check-expect (printable-keys-search (make-buffer (list "Line 0") 0 0 FSIZE-EDIT "SEARCH") "!")
              (make-buffer (list "!Line 0") 0 1 FSIZE-EDIT "SEARCH"))
             
(check-expect (printable-keys-search buffer-search "!")
              (make-buffer (list "Line !0" "Line 1") 0 6 10 "SEARCH"))

(define (printable-keys-search buffer key)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define line  (list-ref lol lnum))]
    (make-buffer (subst (char-insert key cnum line) lnum lol)
                 lnum (add1 cnum) fsize "SEARCH")))

; EXERCISE 5

; string-search : String String Nat -> Nat
; outputs position of first occurrance of first string in second string given starting position.
; if it is not found, returns length of second string plus 1

(check-expect (string-search "pattern" "pattern string test"  0)  0) ; an easy search
(check-expect (string-search "pattern" "pattern string test"  1) 20) ; not found at pos. >= 1
(check-expect (string-search "pattern" "pattern string test" 30) 20) ; bad start position: 30
(check-expect (string-search "string"  "pattern string test"  0)  8) ; found at pos. 8
(check-expect (string-search "string"  "pattern string test"  8)  8) ; same answer as previous!
(check-expect (string-search "string"  "pattern string test"  9) 20) ; not found at pos. >= 9
(check-expect (string-search ""        "pattern string test"  0)  0) ; "" always matches
(check-expect (string-search "paddern" "pattern string test"  0) 20) ; note "...dd...": not found
(check-expect (string-search "hi"      "hi\nhello\nyihi\nyo\n" 7) 11)

(define (string-search s t p)
  (if (and (< p (string-length t)) (string-contains? s (substring t p)))
      (find-string s t p)
      (add1 (string-length t))))

; find-string : String String Nat -> Nat
; returns position where first occurrance of first string appears in second string

(check-expect (find-string "pattern" "pattern string test"  0)  0) 
(check-expect (find-string "string"  "pattern string test"  0)  8) 
(check-expect (find-string "string"  "pattern string test"  8)  8) 
(check-expect (find-string ""        "pattern string test"  0)  0) 

(define (find-string s t p)
  (local [; find-string/acc : String String Nat Nat -> Nat
          ; same purpose, but using an accumulator: position
          (define (find-string/acc s t p acc)
            (if (string=? s (substring t p (+ p (string-length s))))
                acc
                (find-string/acc s (substring t 1) p (add1 acc))))]
    (find-string/acc s t p p)))

; BONUS QUESTION:

#|
We cannot just return lt because that could imply that the last character
is equal to the search string.
For example, if s = "x" (any 1String) and t = "everybody", having it return the length of "everybody",
would imply that x is equal to y which is not necessarily the case.
Therefore, to make it unambigious and be sure that it is completely out of range,
we should add one to the length.
|#

; EXERCISE 6:

; search : Buffer -> Buffer
; returns the buffer with the cursor position adjusted and in edit mode

(check-expect (search (make-buffer (list "Line 0" "Line 1" "Line 0") 1 0 10 "SEARCH"))
              (make-buffer (list "Line 1" "Line 0") 1 0 10 "EDIT"))
(check-expect (search (make-buffer (list "Line 1" "Line 1" "Line 0") 1 0 10 "SEARCH"))
              (make-buffer (list "Line 1" "Line 0") 0 0 10 "EDIT"))
(check-expect (search (make-buffer (list "" "Line 0" "Line 1") 1 0 10 "SEARCH"))
              (make-buffer (list "Line 0" "Line 1") 0 0 10 "EDIT"))
(check-expect (search (make-buffer (list "hi" "hello" "yihi" "yo") 1 3 10 "SEARCH"))
              (make-buffer (list "hello" "yihi" "yo") 1 2 10 "EDIT"))
(check-expect (search (make-buffer (list "yoh" "hello" "yihi" "yo") 1 3 10 "SEARCH"))
              (make-buffer (list "hello" "yihi" "yo") 1 3 10 "EDIT"))

(define (search buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define line  (list-ref lol lnum))
          (define string-pos (string-search (first lol) (lol->text lol)
                                            (sub1 (lnum-cnum->pos lol lnum cnum))))
          (define new-pos
            (pos->lnum-cnum lol
                            (add1 (string-search (first lol)
                                                 (lol->text lol)
                                                 (sub1 (lnum-cnum->pos lol lnum cnum))))))]
    (if (> string-pos (string-length (lol->text lol)))
        (make-buffer (rest lol) lnum cnum fsize "EDIT")
        (make-buffer (rest lol) (sub1 (posn-x new-pos)) (posn-y new-pos) fsize "EDIT"))))

; lnum-cnum->pos : [NEList-of Line] Nat Nat -> Nat
; returns the position from the cursor position

(check-expect (lnum-cnum->pos (list "hi" "hello" "yihi" "yo") 1 3) 7)  
(check-expect (lnum-cnum->pos (list "Line 0" "Line 1" "Line 0") 1 0) 8)
(check-expect (lnum-cnum->pos (list "Line 1" "Line 1" "Line 0") 1 0) 8)
(check-expect (lnum-cnum->pos (list "Line 1" "Line 0" "Line 1") 2 3) 18)
(check-expect (lnum-cnum->pos (list "Line 1" "Line 0" "Line 1" "Line 2") 3 5) 27)
(check-expect (lnum-cnum->pos (list "Line 1" "Line 0" "Line 1" "Line 2") 3 0) 22)

(define (lnum-cnum->pos lol lnum cnum)
  (local [; lnum-cnum->pos/acc : [NEList-of Line] Nat Nat Nat Nat -> Nat
          ; same as function wtih 2 accumulator: position of the cursor and line number
          ; check expect : same as function
          (define (lnum-cnum->pos/acc lol lnum cnum count acc)
            (if (= count 0)
                (+ 1 cnum acc)
                (lnum-cnum->pos/acc (rest lol) lnum cnum (sub1 count)
                                    (+ acc (add1 (string-length (first lol)))))))]
    (lnum-cnum->pos/acc lol lnum cnum lnum 0)))

; pos->lnum-cnum : [NEList-of Line] Nat -> Posn
; makes a position based off of the line and column number of the cursor

(check-expect (pos->lnum-cnum (list "hi" "hello" "yihi" "yo") 12) (make-posn 2 2))
(check-expect (pos->lnum-cnum (list "Line 0" "Line 1" "Line 0") 15) (make-posn 2 0))
(check-expect (pos->lnum-cnum (list "Line 1" "Line 1" "Line 0") 8) (make-posn 1 0))
(check-expect (pos->lnum-cnum (list "Line 1" "Line 0" "Line 2" "Line 1") 22) (make-posn 3 0))
(check-expect (pos->lnum-cnum (list "" "Line 0" "Line 1" "Line 2") 2) (make-posn 1 0))

(define (pos->lnum-cnum lol pos)
  (cond [(or (empty? lol) (< pos (string-length (first lol)))) (make-posn 0 (sub1 pos))]
        [else
         (local [(define new-pos (pos->lnum-cnum (rest lol)
                                                 (- pos (+ 1 (string-length (first lol))))))]
           (make-posn (add1 (posn-x new-pos)) (posn-y new-pos)))]))

; EXERCISE 7

(define SEARCH-TEXT (list "Type in the string you want to search"
                          "Accepts any printable keys as well as return and backspace"
                          "Press F3 when you want to execute your search."
                          "Enter your search string below:"))

(define SEARCH-FONT-SIZE 25)
(define SEARCH-FONT-COLOR "purple")

; search-image : [List-of String] -> Image
; converts a list of strings into an image

(check-expect (search-image (list "hi" "hello")) (above
                                                  (text/font "hi" SEARCH-FONT-SIZE SEARCH-FONT-COLOR
                                                             "Monospace" "default"
                                                             "normal" "normal" #f)
                                                  (text/font "hello" SEARCH-FONT-SIZE
                                                             SEARCH-FONT-COLOR "Monospace"
                                                             "default" "normal" "normal" #f)))
(check-expect (search-image SEARCH-TEXT)
              (above
               (text/font "Type in the string you want to search"
                          SEARCH-FONT-SIZE SEARCH-FONT-COLOR "Monospace" "default"
                          "normal" "normal" #f)
               (text/font "Accepts any printable keys as well as return and backspace"
                          SEARCH-FONT-SIZE SEARCH-FONT-COLOR "Monospace" "default"
                          "normal" "normal" #f)
               (text/font "Press F3 when you want to execute your search."
                          SEARCH-FONT-SIZE SEARCH-FONT-COLOR "Monospace" "default"
                          "normal" "normal" #f)
               (text/font "Enter your search string below:"
                          SEARCH-FONT-SIZE SEARCH-FONT-COLOR "Monospace" "default"
                          "normal" "normal" #f)))
              
                                              
(define (search-image los)
  (foldr above empty-image (map (lambda (s) (text/font s SEARCH-FONT-SIZE SEARCH-FONT-COLOR
                                                       "Monospace" "default" "normal"
                                                       "normal" #f)) los)))

; process-search : Buffer -> Image
; Draws new buffer in "SEARCH" mode

(check-expect (process-search (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU"))
              (overlay (above/align "center" (search-image SEARCH-TEXT)
                                    (line->image "Line 0" #f SEARCH-FONT-SIZE)) BACKGROUND))
              
(check-expect (process-search buffer-text-empty)
              (overlay (above/align "center" (search-image SEARCH-TEXT)
                                    (line->image "" #f SEARCH-FONT-SIZE)) BACKGROUND))
              
(define (process-search buffer)
  (local [(define lol   (buffer-lol   buffer))
          (define lnum  (buffer-lnum  buffer))
          (define cnum  (buffer-cnum  buffer))
          (define fsize (buffer-fsize buffer))
          (define mode  (buffer-mode  buffer))
          (define line  (list-ref lol lnum))
          (define search-line
            (line->image (first lol) #f SEARCH-FONT-SIZE))]
    (overlay (above/align "center" (search-image SEARCH-TEXT) search-line) BACKGROUND)))