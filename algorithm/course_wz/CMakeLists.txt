cmake_minimum_required(VERSION 3.10)
project(course_wz)

set(CMAKE_CXX_STANDARD 11)

include_directories(.)
include_directories(c-cpp)
include_directories(c-cpp/09_queue)
include_directories(c-cpp/15_)
include_directories(c-cpp/16_bsearch)

add_executable(course_wz
        c-cpp/09_queue/array_queue.h
        c-cpp/09_queue/array_queue_test.cpp
        c-cpp/main.cpp)
