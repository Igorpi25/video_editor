
Future<List<T>> runAll<T>(List<Future<T>> futures) async {
  return Future.wait(futures);
}
void main() {
  Future<int> v1 = Future.value(1);
  Future<int> v2 = Future.value(2);

  runAll<int>([v1,v2]).then((r) {
    print(r);
  });
}