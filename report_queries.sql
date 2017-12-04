--Report Queries
--Room occupancy and revenue by type
SELECT r_class, SUM(checkout - checkin) AS nights_occupied, SUM((checkout - checkin) * price) AS income 
FROM roombooking NATURAL JOIN roomrate
WHERE 
	(checkin <= '18/12/2017' AND checkout >= '18/12/2017') 
	OR 
	(checkin >= '18/12/2017' AND checkout <= '31/12/2017') 
GROUP BY r_class;

--Total availability of rooms
SELECT r_class, COUNT(r_class) * ('31/12/2017'::date - '18/12/2017'::date) AS nights_avail FROM room GROUP BY r_class;

--Summary between dates
SELECT occ.r_class, nights_occupied, income, occ.nights_occupied::numeric / avail.nights_avail::numeric AS percent_occupancy FROM
	(SELECT r_class, COUNT(r_class) * ('31/12/2017'::date - '18/12/2017'::date) AS nights_avail FROM room GROUP BY r_class) avail
	JOIN
	(SELECT r_class, SUM(checkout - checkin) AS nights_occupied, SUM((checkout - checkin) * price) AS income 
	FROM roombooking NATURAL JOIN roomrate
	WHERE 
		(checkin <= '18/12/2017' AND checkout >= '18/12/2017') 
		OR 
		(checkin >= '18/12/2017' AND checkout <= '31/12/2017') 
	GROUP BY r_class) occ
ON occ.r_class = avail.r_class;


CREATE OR REPLACE FUNCTION weekly_reports(start_date date, end_date date) RETURNS TABLE (
	r_class character(5),
	nights_occupied bigint,
	income numeric,
	percent_occupancy numeric,
	date_start date,
	date_end date
)
AS $$
DECLARE 
week_start date;
week_end date;
r record;
	BEGIN
		--Find the monday before the start date
		--Thanks to https://stackoverflow.com/questions/27989762/get-this-weeks-mondays-date-in-postgres
		SELECT start_date - (cast(extract(isodow from start_date) as int)-1) INTO week_start;
		LOOP
		EXIT WHEN week_start > end_date;
			week_end := start_date + 7;
			FOR r IN (
				SELECT occ.r_class, occ.nights_occupied, occ.income, ROUND(occ.nights_occupied::numeric / avail.nights_avail::numeric * 100, 2) AS percent_occupancy FROM
					(SELECT room.r_class, COUNT(room.r_class) * (week_end - week_start) AS nights_avail FROM room GROUP BY room.r_class) avail
					JOIN
					(SELECT roomrate.r_class, SUM(checkout - checkin) AS nights_occupied, SUM((checkout - checkin) * price) AS income 
					FROM roombooking NATURAL JOIN roomrate
					WHERE 
						(checkin <= week_start AND checkout >= week_start) 
						OR 
						(checkin >= week_start AND checkout <= week_end) 
					GROUP BY roomrate.r_class) occ
				ON occ.r_class = avail.r_class
				)
			LOOP
				r_class := r.r_class;
				nights_occupied := r.nights_occupied;
				income := r.income;
				percent_occupancy := r.percent_occupancy;
				date_start := week_start;
				date_end := week_end;
				RETURN NEXT;
			END LOOP;
			week_start := week_start + 7;
		END LOOP;
	END;
$$ LANGUAGE plpgsql;
