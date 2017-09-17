package matsu_chara.quill_free.repo

import io.getquill.monad.Effect
import matsu_chara.quill_free.Person
import matsu_chara.quill_free.quill.MyDbContext

class PersonFreeRepository {

  def insert(p: Person)(implicit ctx: MyDbContext): ctx.IO[Unit, Effect.Write] = {
    import ctx._
    val q = quote {
      query[Person].insert(lift(p))
    }
    runIO(q).map(_ => ())
  }

  def findById(id: Long)(implicit ctx: MyDbContext): ctx.IO[Option[Person], Effect.Read] = {
    import ctx._
    val q = quote {
      query[Person].filter(_.id == lift(id))
    }
    runIO(q).map(_.headOption)
  }

  def deleteAll()(implicit ctx: MyDbContext): ctx.IO[Unit, Effect.Write] = {
    import ctx._
    val q = quote {
      query[Person].delete
    }
    runIO(q).map(_ => ())
  }
}
