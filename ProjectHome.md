Bigflag is a data structure which allows you to manage a large amount of boolean flags effectively.
In a specific condition, it can have billions flags in only hundreds of bytes.

Bigflag is suitable for preservation of a large amount of numeric data, ID management, sorting integer and so on.

# ID management #

For example, you can create unique ID easily as follow:

```
class IDManager{
  Bigflag bf = new Bigflag();
  int createID(){
    int id = bf.seachOff(0, 1).get(0);
    bf.on(id);
    return id;
  }
  
  void cancelID(int id){
    bf.off(id);
  }
}

IDManager idm = new IDManager();
idm.createID(); // => 0
idm.createID(); // => 1
idm.createID(); // => 2
idm.createID(); // => 3
idm.createID(); // => 4
idm.createID(); // => 5


idm.cancelID(1);
idm.cancelID(3);
idm.cancelID(4);

idm.createID(); // => 1
idm.createID(); // => 3
idm.createID(); // => 4
idm.createID(); // => 6
```

Canceled ID can be recycled automatically.
The total memory of Bigflag does not increase even if the large amount of IDs is created.


# sorting integer #

You can write sorting program easilly as follows:

```
Bigflag bf = new Bigflag();
bf.on(4590);
bf.on(8);
bf.on(30);
bf.on(69011);
bf.on(409);

for(int num : bf) {
  System.out.println(num);
}
// 8, 30, 409, 4590, 69011
```

It is faster than Java original library, 'Collections.sort()'.
I measured the performance sorting an ArrayList instance which size is 100,000. The result is:
- Collections.sort : 301 msec
- Bigflag : 26 msec

Note: Bigflag cannot sort the list with the duplication of data.


Bigflagは複数段の階層構造になっており、末端のノードが256個のフラグを保持しています。
末端のノードはその一つ上の階層が保持し、更にそのノードをもう一つ上の階層のノードが保持し･･･、という繰り返しで、合計で7階層になっています。
末端以外のノードは下位のノードを16個保持しており、最上位のノードは一つになります。
各ノードに保持されているフラグが全てtrue or falseの場合、そのノードは削除され、そのノードの上位ノードにそのことが記録されます。
このようにして、メモリ効率よく大量のフラグを管理することを可能としています。