//
// Created by admin on 2019/9/10.
//

#include <iostream>
#include "array_queue.h"

int main() {
//    std::cout << "haha" << std::endl;

    auto do_traverse = [&](auto itme){std::cout << ' '; };
    ArrayQueue<int> array_queue_1(3);
    array_queue_1.enqueue(1);
    array_queue_1.enqueue(2);
    array_queue_1.enqueue(3);
    // array_queue_1.enqueue(4);  // throw
    array_queue_1.traverse(do_traverse);
    std::cout << std::endl;
    return  0;
}