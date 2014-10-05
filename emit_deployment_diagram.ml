#!/usr/bin/env ocaml

let tau = 8. *. (atan 1.);;
let rad2deg theta = 360. *. theta /. tau;;

let list_iter3 f lst =
    let rec aux prev = begin function
        | [] -> ()
        | x :: [] -> f prev x None
        | x :: y :: rem -> f prev x (Some y); aux (Some x) (y::rem)
    end in aux None lst;;

let list_enumerate lst =
    let i = ref (-1) in
    List.map (fun x -> incr i; (!i, x)) lst;;

let emit_lines color points =
    Printf.printf "\\draw[-, color=%s] " color;
    list_iter3 (fun prev (x,y) next ->
        let trail = if next = None then "" else " -- " in
        Printf.printf "(%f, %f)%s" x y trail
    ) points;
    Printf.printf ";\n%!";;

let apply_polar_movement (mag, theta) (x, y) =
    let (dx, dy) = ((mag *. (cos theta)), (mag *. (sin theta))) in
    (x +. dx, y +. dy);;

let angle_between (x1, y1) (x2, y2) =
    let (dx, dy) = (x2-.x1, y2-.y1) in
    atan2 dy dx;;

let emit_textline (x1, y1) (x2, y2) str =
    let theta = rad2deg (angle_between (x1, y1) (x2, y2)) in
    Printf.printf "\\draw[->] (%f, %f) -- node[above, rotate=%f]{%s} (%f, %f);\n%!" x1 y1 theta str x2 y2;;

let emit_text color (x, y) str =
    Printf.printf "\\draw[color=%s] (%f, %f) node[right]{%s};\n%!" color x y str;;

let emit_rectangle color (x1, y1) (x2, y2) =
    emit_lines color [(x1, y1);(x2,y1);(x2,y2);(x1,y2);(x1,y1)];;

let emit_3dbox color (x1, y1) (x2, y2) (z, theta) =
    let transform = apply_polar_movement (z, theta) in
    emit_rectangle color (x1, y1) (x2, y2);
    emit_lines color [(x1, y1); transform (x1, y1); transform (x2, y1); transform (x2, y2); (x2, y2)];
    emit_lines color [(x2, y1); transform (x2, y1)];;

let emit_3d_textbox color (x, y) w strs =
    let numlines = 1+List.length strs in
    let h = 0.5 *. float_of_int numlines in
    emit_3dbox color (x,y) (x+.w, y-.h) (0.25, tau /. 8.);
    List.iter (fun (i, str) ->
        emit_text color (x, y-.(0.5 *. float_of_int (i+1))) str
    ) (list_enumerate strs);;

let emit_lined_textbox color (x, y) w title strs =
    emit_3d_textbox color (x, y) w (title::strs);
    let delta = 0.75 in
    emit_lines color [(x,y-.delta); (x+.w,y-.delta)];;

(*emit_lined_textbox "black" (0.,0.) 5. "This" ["is";"a";"test"];;*)
List.iter (fun (p,browser) ->
    emit_lined_textbox "black" p 5. "Client" [Printf.sprintf "%s browser" browser]
    ) [(0.,0.0),"Firefox/Iceweasel";
       (0.,-2.5),"Chrome";
       (0.,-5.0),"Lynx";
       (0.,-7.5),"Safari"];;
emit_lined_textbox "black" (10., -1.) 6. "Server"
    ["Java's built in HttpServer object";
     "PostgreSQL database"];;

List.iter (fun (p1, p2) -> emit_textline p1 p2 "HTTP/HTTPS")
    [(5.1,-0.7),(10.,-1.5);
     (5.1,-3.2),(10.,-1.7);
     (5.1,-5.7),(10.,-1.9);
     (5.1,-8.2),(10.,-2.1);];;
