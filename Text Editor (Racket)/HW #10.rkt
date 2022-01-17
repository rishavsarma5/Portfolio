;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |HW #10|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; EXERCISE 1:

(require 2htdp/image)
(require 2htdp/universe)

; A Div is one of:
; - String
; - Image
; - UL
; - OL
; an HTML page: a text, a picture, an unordered, or an ordered list.
(define div-1 "Hi!")
(define div-2 (circle 10 "solid" "red"))
(define (div-templ d)
  (... (cond [(string? d) ... d ...]
             [(image?  d) ... d ...]
             [(ul?     d) ... (ul-templ d) ...]
             [(ol?     d) ... (ol-templ d) ...])))
 
(define-struct ul [content])
(define-struct ol [content])
 
; A UL is a (make-ul [List-of Div])
; an unordered list of HTML divs
(define ul-0 (make-ul '()))
(define ul-1 (make-ul (list div-1 div-2)))
(define (ul-templ ul)
  (... (lod-templ (ul-content ul)) ...))
 
; A OL is a (make-ol [List-of Div])
; an ordered list of HTML divs
(define ol-0 (make-ol '()))
(define ol-1 (make-ol (list div-1 ul-1)))
(define (ol-templ ol)
  (... (lod-templ (ol-content ol)) ...))
 
(define div-3 ul-1)
(define div-4 ol-1)

; num-divs : Div -> Nat
; returns the total number of Divs inside of one Div, including itself

(check-expect (num-divs div-1) 1)
(check-expect (num-divs div-2) 1)
(check-expect (num-divs div-3) 3)
(check-expect (num-divs div-4) 5)

(define (num-divs d)
  (cond [(string? d) 1]
        [(image?  d) 1]
        [(ul?     d) (+ 1 (count-ul d))]
        [(ol?     d) (+ 1 (count-ol d))]))

; count-ul : Div -> Nat
; returns the number of divs inside the UL

(check-expect (count-ul ul-0) 0)
(check-expect (count-ul ul-1) 2)

(define (count-ul ul)
  (foldr + 0 (map num-divs (ul-content ul))))

; count-ol : Div -> Nat
; returns the number of divs inside the OL

(check-expect (count-ol ol-0) 0)
(check-expect (count-ol ol-1) 4)

(define (count-ol ol)
  (foldr + 0 (map num-divs (ol-content ol))))

; EXERCISE 2:

(define TEXT-SIZE  20)
(define TEXT-COLOR "black")
 
(define im-1 (square 10 "solid" "blue"))
(define im-2 (circle 10 "solid" "red"))
 
(check-expect (enumerate (list im-1 im-2))
              (list (beside (text "1. " TEXT-SIZE TEXT-COLOR) im-1)
                    (beside (text "2. " TEXT-SIZE TEXT-COLOR) im-2)))
(check-expect (enumerate (list im-1))
              (list (beside (text "1. " TEXT-SIZE TEXT-COLOR) im-1)))
(check-expect (enumerate '()) '())

; enumerate : [List-of Images] -> [List-of Images]
; takes a list of images and produces an enumerated list of Images
 
(define (enumerate loi)
  (local [; nlist->ilist : [List-of String] -> [List-of Image]
          ; converts list of string into a list of image
          ; (check-expect (nlist->ilist (list "1. "))
          ;                             (list (text "1. " TEXT-SIZE TEXT-COLOR)))
          ; (check-expect (nlist->ilist (list "1. " "2. "))
          ;                             (list (text "1. " TEXT-SIZE TEXT-COLOR)
          ;                                   (text "2. " TEXT-SIZE TEXT-COLOR)))
          (define (nlist->ilist los)
            (map (lambda (x) (text x TEXT-SIZE TEXT-COLOR)) los))]
    (local [; numeric-list : [List-of Images] -> [List-of String]
            ; generates a list of string, with enumerations
            ; (check-expect (numeric-list (list im-1)) (list "1. "))
            ; (check-expect (numeric-list (list im-2)) (list "1. "))
            ; (check-expect (numeric-list (list im-1 im-2)) (list "1. " "2. "))
            (define (numeric-list loi)
              (map (lambda (x) (string-append (number->string x) ". "))
                   (build-list (length loi) add1)))]
      (beside-images (nlist->ilist (numeric-list loi)) loi))))


; beside-images : [List-of Image] [List-of Image] -> [List-of Image]
; combines two lists of images into one enumerated list of images

(check-expect (beside-images (list (text "1. " TEXT-SIZE TEXT-COLOR)) (list im-2))
              (list (beside (text "1. " TEXT-SIZE TEXT-COLOR) im-2)))
(check-expect (beside-images (list (text "1. " TEXT-SIZE TEXT-COLOR)
                                   (text "2. " TEXT-SIZE TEXT-COLOR)) (list im-2 im-2))
              (list (beside (text "1. " TEXT-SIZE TEXT-COLOR) im-2)
                    (beside (text "2. " TEXT-SIZE TEXT-COLOR) im-2)))
(check-expect (beside-images '() '()) '())

(define (beside-images loi1 loi2)
  (cond [(empty? loi1) '()]
        [else (cons (beside (first loi1) (first loi2))
                    (beside-images (rest loi1) (rest loi2)))]))

; EXERCISE 3

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
; render the buffer in edit or menu mode, as the case may be

(check-expect (draw-buffer buffer-text-empty) (draw-buffer-text-mode buffer-text-empty))
(check-expect (draw-buffer (make-buffer (list "") 0 0 DEFAULT-FONT-SIZE "MENU"))
              (process-menu (make-buffer (list "") 0 0 DEFAULT-FONT-SIZE "MENU")))

(define (draw-buffer buffer)
  (cond [(string=? (buffer-mode buffer) "EDIT") (draw-buffer-text-mode buffer)]
        [(string=? (buffer-mode buffer) "MENU") (process-menu buffer)]))

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
   "f3" "f4" "f5" "f6" "f7" "f8" "f9" "f10" "f11" "f12"
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
  (process-key-text-mode buffer key))

; process-key-text-mode : Buffer Key -> Buffer
; respond to each key event in text mode

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
(check-expect (process-key-text-mode
               (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU") "escape")
              (process-text-menu (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU")))
(check-expect (process-key-text-mode
               (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU") "5")
              (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU"))

(define (process-key-text-mode buffer key)
  (if (string=? (buffer-mode buffer) "EDIT")
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
            [else                 (process-text-printable buffer key)])
      (cond [(key=? key "escape") (process-text-menu buffer)]
            [else buffer])))

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

; EXERCISE 4

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
; process the "f1" key, which will increase the font size

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

; EXERCISE 5

(define MENU-TEXT (list "Modes : EDIT MENU SEARCH (press ESCAPE to toggle between modes)"
                        "To increase font size or decrease font size, press F1 and F2."))
(define FONT-SIZE 20)
(define FONT-COLOR "blue")
                   
; process-text-menu : Buffer -> BUFFER
; allows user to toggle modes EDIT and MENU

(check-expect (process-text-menu buffer-text)
              (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU"))
(check-expect (process-text-menu buffer-text-empty)
              (make-buffer (list "") 0 0 FSIZE-EDIT "MENU"))
(check-expect (process-text-menu (make-buffer (list "Line 0" "Line 1") 1 3 FSIZE-EDIT "MENU"))
              buffer-text)
              
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
               (text/font
                "Modes : EDIT MENU SEARCH (press ESCAPE to toggle between modes)"
                FONT-SIZE FONT-COLOR
                "Monospace" "default" "normal" "normal" #f)
               (text/font "To increase font size or decrease font size, press F1 and F2."
                          FONT-SIZE FONT-COLOR "Monospace" "default" "normal" "normal" #f)))
                                                
(define (menu-image los)
  (foldr above empty-image (map (lambda (s) (text/font s FONT-SIZE FONT-COLOR
                                                       "Monospace" "default" "normal"
                                                       "normal" #f)) los)))

; EXERCISE 6

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

; EXERCISE 7

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

; EXERCISE 8

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

; EXERCISE 9

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
