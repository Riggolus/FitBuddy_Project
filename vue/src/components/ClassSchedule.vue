<template>
    <div class="container">
        <div class="calendar">
            <div class="heading">
                <div class="month"></div>
                <div class="btns">
                    <div class="btn today-btn">
                        Today
                    </div>
                    <div class="btn prev-btn">
                        Last
                    </div>
                    <div class="btn next-btn">
                        Next
                    </div>
                </div>
            </div>
            <div class="weekdays">
                <div class="day">Sun</div>
                <div class="day">Mon</div>
                <div class="day">Tue</div>
                <div class="day">Wed</div>
                <div class="day">Thu</div>
                <div class="day">Fri</div>
                <div class="day">Sat</div>
            </div>
            <div class="days">


            </div>
        </div>
    </div>
</template>
<script>

</script>
<style>
header {
    margin: 0px;    
    background-color: white;
}
h1 {
    padding: 20px;
}


* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: "Montserrat", sans-serif;
}




body {
    background-color:lightgray
}
.container {
    width: 100%;
    min-height: 75vh;
    display: flex;
    align-items: center;
    justify-content: center;
}
.calendar {
    width: 100%;
    max-width: 800px;
    padding: 30px 20px;
    border-radius: 10px;
    background-color: white;
}
.calendar .heading {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
}
.calendar .heading .month {
    display:flex;
    align-items: center;
    font-size: 25px;
    font-weight: 600;
    color: black;
}
.calendar .heading .btns {
    display:flex;
    gap: 10px;
}
.calendar .heading .btns .btn {
    width: 50px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 5px;
    color: black;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.2s ease-in-out;
}
.calendar .heading .btns .btn:hover {
    transform: scale(1.25);
}
.weekdays {
    display: flex;
    gap: 10px;
    margin-bottom: 10px;  
}
.weekdays .day {
    width: calc(100% / 7 - 10px);
    text-align: center;
    font-size: 16px;
    font-weight: 600;  
}
.days {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;  
}
.days .day {
    width: calc(100% / 7 - 10px);
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 5px;
    font-size: 16px;
    font-weight: 400;
    color: black;
    background-color: whitesmoke;
    cursor: pointer;
    transition: all 0.2s ease-in-out;
}
.days .day:not(.next):not(.prev):hover {
    background-color: lightgrey;
    transform: scale(1.10);
}
.days .day.today {
    background-color: lightgrey;
}
.days .day.next,
.days .day.prev {
    color: grey
}

#searchMember {
    margin-top: 50px;
    margin-left: 55px;
}
table, th, td, tr {
    background-color: white;
    border: 3px;
    margin-left: 54px;
    padding: 5px;
    width: 500px;
    text-align: left;
    transition: all 0.1s ease-in-out;
    cursor: pointer;
  }
  tr:hover {
    transform: scale(1.05);
    background-color: grey;
}

</style>