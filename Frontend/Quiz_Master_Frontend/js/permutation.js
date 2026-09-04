(function () {
  // Seed is an index from 0..23 in the frontend. Your backend description
  // says the seed is in the range 1..24, so normalize it here.
  function getPermutation(seed) {
    let index = Number(seed);
    if (!Number.isInteger(index)) throw new RangeError('Seed must be an integer.');
    if (index >= 1 && index <= 24) index -= 1;
    if (index < 0 || index >= 24) {
      throw new RangeError('Seed must identify one of the 24 permutations.');
    }

    const numbers = [1, 2, 3, 4];
    const permutation = [];
    const factorials = [6, 2, 1, 1];

    for (let i = 0; i < 4; i++) {
      const pos = Math.floor(index / factorials[i]);
      index %= factorials[i];
      permutation.push(numbers.splice(pos, 1)[0]);
    }
    return permutation;
  }

  function toViewOptions(question) {
    const permutation = getPermutation(question.seed);
    const options = [question.option1, question.option2, question.option3, question.option4];
    return permutation.map(number => ({ number, text: options[number - 1] }));
  }

  function actualAnswerFromViewIndex(viewIndex, permutation) {
    if (viewIndex < 0 || viewIndex >= permutation.length) return -1;
    return permutation[viewIndex];
  }

  window.Permutation = { getPermutation, toViewOptions, actualAnswerFromViewIndex };
})();
