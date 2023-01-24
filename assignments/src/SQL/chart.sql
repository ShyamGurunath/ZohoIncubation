select
    cd0.m,cd0.g
    from (
            (
                select
                row_number() over(order by g) as d0 ,*
                from (
                        select sum(a) as m ,
                        concat(  cast(year(d) as varchar) ,'-',
                        if(month(d) < 10,
                        concat('0' ,'', cast(month(d) as varchar)),cast(month(d) as varchar)) ,
                        '-', if(day(d) < 10 , concat('0','' ,cast(day(d) as varchar)) , cast(day(d) as varchar))  )
                        as g from (
                                    select s.date as d,
                                       coalesce(a,0) as a  from  (
                            s                                       select cast(date as date) as date
                                                                     from unnest (
                                                                            sequence(
                                                                                date(TIMESTAMP '2022-11-29 00:00')
                                                                                , date(TIMESTAMP '2022-12-28 16:00'),
                                                                                INTERVAL '1' DAY)) t(date)) s left join ( select count(*) as a , cast(eventtime as date) as d
                                                                                from webanalytics.signupinfo_zoho_all  a
                                                                                where  ( eventtime between  cast('2022-11-29 00:00' as timestamp) and cast('2022-12-28 16:00' as timestamp) and ( ( partitionmonth= '2022_11' ) or ( partitionmonth= '2022_11_1' ) or ( partitionmonth= '2022_12' ) or ( partitionmonth= '2022_12_1' ) or ( partitionmonth= '2022_13' ) or ( partitionmoselect cd0.m,cd0.g from ((select row_number() over(order by g) as d0 ,* from (select sum(a) as m , concat(  cast(year(d) as varchar) ,'-', if(month(d) < 10, concat('0' ,'', cast(month(d) as varchar)),cast(month(d) as varchar)) ,'-', if(day(d) < 10 , concat('0','' ,cast(day(d) as varchar)) , cast(day(d) as varchar))  ) as g from ( select s.date as d,coalesce(a,0) as a  from  (select cast(date as date) as date from unnest (sequence(date(TIMESTAMP '2022-11-29 00:00'), date(TIMESTAMP '2022-12-28 16:00'),INTERVAL '1' DAY)) t(date)) s left join ( select count(*) as a , cast(eventtime as date) as d from webanalytics.signupinfo_zoho_all  a  where  ( eventtime between  cast('2022-11-29 00:00' as timestamp) and cast('2022-12-28 16:00' as timestamp) and ( ( partitionmonth= '2022_11' ) or ( partitionmonth= '2022_11_1' ) or ( partitionmonth= '2022_12' ) or ( partitionmonth= '2022_12_1' ) or ( partitionmonth= '2022_13' ) or ( partitionmonth= '2022_13_1' )  )   )  and (eventnumber = 1) group by cast(eventtime as date) ) v on s.date = v.d) group by  concat(  cast(year(d) as varchar) ,'-', if(month(d) < 10, concat('0' ,'', cast(month(d) as varchar)),cast(month(d) as varchar)) ,'-', if(day(d) < 10 , concat('0','' ,cast(day(d) as varchar)) , cast(day(d) as varchar))  ) )) cd0 )
                                                                                order by cd0.gnth= '2022_13_1' )  )   )  and (eventnumber = 1)
                                                                                group by cast(eventtime as date) ) v on s.date = v.d) group by  concat(
                                                                                cast(year(d) as varchar) ,'-', if(month(d) < 10, concat('0' ,'',
                                                                                cast(month(d) as varchar)),cast(month(d) as varchar)) ,'-', if(day(d) < 10 , concat('0','' ,cast(day(d) as varchar)) , cast(day(d) as varchar))  ) )) cd0 ) order by cd0.g